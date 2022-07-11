package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import androidx.lifecycle.viewModelScope
import com.dolgalyovviktor.superdogroceries.common.arch.Dispatchers
import com.dolgalyovviktor.superdogroceries.common.arch.ReduxViewModel
import com.dolgalyovviktor.superdogroceries.domain.ProductService
import com.dolgalyovviktor.superdogroceries.feature.product_list.router.ProductListRouter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductListViewModel(
    private val productService: ProductService,
    reducer: ProductListReducer,
    mapper: ProductListStateModelMapper,
    dispatchers: Dispatchers
) : ReduxViewModel<ProductListAction, ProductListChange, ProductListState, ProductListPresentationModel, ProductListRouter>(
    reducer = reducer,
    stateToModelMapper = mapper,
    dispatchers = dispatchers
) {
    override var state = ProductListState.EMPTY
        set(value) {
            val isListeningStateChanged = field.isListening != value.isListening
            field = value
            if (isListeningStateChanged) {
                if (value.isListening) startListening() else stopListening()
            }
        }
    private var listeningJob: Job? = null

    override fun onObserverActive(firstTime: Boolean) {
        super.onObserverActive(firstTime)
        if (state.isListening) startListening()
    }

    override fun onObserverInactive() {
        super.onObserverInactive()
        listeningJob?.cancel()
    }

    override suspend fun provideChangesObservable(): Flow<ProductListChange> = emptyFlow()

    override fun processAction(action: ProductListAction) {
        when (action) {
            ProductListAction.ChangeListeningStateClick -> sendChange(
                ProductListChange.ListeningStateChanged(isListening = !state.isListening)
            )
        }
    }

    private fun startListening() {
        listeningJob = viewModelScope.launch(dispatchers.background) {
            productService.observeProducts()
                .catch { e -> Timber.e(e) }
                .onStart { sendChange(ProductListChange.ProductsCleared) }
                .onEach { sendChange(ProductListChange.ProductsAdded(listOf(it))) }
                .onCompletion { sendChange(ProductListChange.ListeningStateChanged(isListening = false)) }
                .launchIn(this)
        }
    }

    private fun stopListening() {
        listeningJob?.cancel()
    }
}