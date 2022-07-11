package com.dolgalyovviktor.superdogroceries.common.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "REDUX"

abstract class ReduxViewModel<A : UIAction, C : UIStateChange, S : UIState, M : UIModel, R : UIRouter>(
    protected val dispatchers: Dispatchers,
    private val reducer: Reducer<S, C>,
    private val stateToModelMapper: StateToModelMapper<S, M>
) : ViewModel() {
    private val modelName = javaClass.simpleName
    private val changes = MutableSharedFlow<C>(
        replay = 5,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    private val actions = Channel<A>(Channel.RENDEZVOUS)
    protected abstract var state: S
    var router: R? = null

    protected open val errorHandler: ErrorHandler = object : ErrorHandler({ ErrorViewAdapter() }) {}

    private val model = object : MutableLiveData<M>() {
        private var hasObserverBeenAttached = false

        override fun onActive() {
            val attachedFirstTime = !hasObserverBeenAttached
            if (!hasObserverBeenAttached) {
                Timber.tag(TAG).d("$modelName: observer attached first time")
                hasObserverBeenAttached = true
                bindChanges()
            } else {
                Timber.tag(TAG).d("$modelName: observer attached")
            }
            onObserverActive(attachedFirstTime)
        }

        override fun onInactive() {
            Timber.tag(TAG).d("$modelName: observer detached")
            onObserverInactive()
        }
    }

    protected val event = object : SingleLiveEvent<UIEvent>() {

        override fun setValue(t: UIEvent?) {
            t?.let {
                if (t.isLoggable()) {
                    Timber.tag(TAG).d("$modelName: event created: ${t.log()}")
                }
            }

            super.setValue(t)
        }
    }

    /**
     * Returns the current presentation model
     */
    val observableModel: LiveData<M> = model

    /**
     * Emits events (only once): Toasts, Errors, Dialogs, etc.
     */
    val observableEvent: LiveData<UIEvent> = event

    init {
        Timber.tag(TAG).d("$modelName: viewModel created")
        viewModelScope.launch(dispatchers.background) {
            actions.consumeEach {
                viewModelScope.launch {
                    try {
                        processAction(it)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }
            }
        }
    }

    /**
     * Dispatches an action. This is the only way to trigger a state/model change.
     */
    fun dispatch(action: A) {
        if (action.isLoggable()) {
            Timber.tag(TAG).d("$modelName: Received action: ${action.log()}")
        }
        viewModelScope.launch(dispatchers.background) { actions.send(action) }
    }

    fun sendChange(change: C) {
        viewModelScope.launch(dispatchers.background) { changes.emit(change) }
    }

    suspend fun sendChangeBlocking(change: C) = changes.emit(change)

    fun sendEvent(ev: UIEvent) {
        viewModelScope.launch { event.value = ev }
    }

    /**
     * Provide Observable which emits [UIStateChange].
     * This change will be converted to new [UIState] with [reducer]
     * */
    protected abstract suspend fun provideChangesObservable(): Flow<C>

    protected open fun onObserverActive(firstTime: Boolean) {}

    protected open fun onObserverInactive() {}

    protected fun onError(error: Throwable) {
        Timber.e(error)
        errorHandler.startErrorHandling(error)
    }

    override fun onCleared() {
        Timber.tag(TAG).d("$modelName: viewModel destroyed")
    }

    protected abstract fun processAction(action: A)

    private fun bindChanges() {
        viewModelScope.launch(dispatchers.background) {
            merge(
                provideChangesObservable().catch { e -> onError(e) },
                changes
            )
                .distinctUntilChanged()
                .flatMapConcat { change ->
                    if (change.isLoggable()) {
                        Timber.tag(modelName).d("change received: ${change.log()}")
                    }
                    flow {
                        emit(reducer.reduce(state, change).also { this@ReduxViewModel.state = it })
                    }
                }
                .distinctUntilChanged()
                .onStart { emit(state) }
                .map { stateToModelMapper.mapStateToModel(it) }
                .distinctUntilChanged()
                .catch { e -> onError(e) }
                .collectLatest {
                    if (it.isLoggable()) {
                        Timber.tag(modelName).i("Model updated: ${it.log()}")
                    }
                    viewModelScope.launch { model.value = it }
                }
        }
    }

    protected suspend inline fun <T> execute(
        crossinline action: suspend () -> T,
        crossinline onStart: () -> Unit = {},
        crossinline onSuccess: (T) -> Unit,
        crossinline onComplete: () -> Unit = {},
        noinline onErrorOccurred: ((Throwable) -> Unit)? = null
    ) {
        onStart()
        try {
            val result = action()
            onSuccess(result)
        } catch (e: Exception) {
            onErrorOccurred?.invoke(e) ?: onError(e)
        }
        onComplete()
    }

    protected suspend inline fun <T> executeBlocking(
        crossinline action: suspend () -> T,
        noinline onStart: (suspend () -> Unit)? = null,
        crossinline onSuccess: suspend (T) -> Unit,
        noinline onComplete: (suspend () -> Unit)? = null,
        noinline onErrorOccurred: ((Throwable) -> Unit)? = null
    ) {
        onStart?.invoke()
        try {
            val result = action()
            onSuccess(result)
        } catch (e: Exception) {
            onErrorOccurred?.invoke(e) ?: onError(e)
        }
        onComplete?.invoke()
    }

    protected suspend inline fun <T> get(
        crossinline action: suspend () -> T,
        crossinline onStart: () -> Unit,
        crossinline onComplete: () -> Unit
    ): T? {
        onStart()
        return try {
            val result = action()
            onComplete()
            result
        } catch (e: Exception) {
            onError(e)
            onComplete()
            null
        }
    }

    protected suspend inline fun <T> getAsFlow(
        crossinline action: suspend () -> T,
        crossinline onStart: () -> Unit = {},
        crossinline onComplete: () -> Unit = {}
    ): Flow<T> {
        onStart()
        return flow {
            try {
                val result = action()
                emit(result)
                onComplete()
            } catch (e: java.lang.Exception) {
                onError(e)
                onComplete()
            }
        }
    }

    protected inner class ErrorViewAdapter : ErrorView {

        override fun showErrorText(text: String) = sendEvent(ErrorEvent.ErrorMessageEvent(text))

        override fun showSomethingGoesWrong() = sendEvent(ErrorEvent.SomethingWrongEvent)
    }
}