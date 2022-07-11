package com.dolgalyovviktor.superdogroceries.common.arch

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal inline val isMainThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

internal fun checkIsMainThread() = check(isMainThread)

fun <F : Fragment, VB : ViewBinding?> viewBinding(
    viewBinder: (F) -> VB?
): ViewBindingProperty<F, VB?> = FragmentViewBindingProperty(viewBinder)

inline fun <F : Fragment, VB : ViewBinding?> viewBinding(
    crossinline vbFactory: (View) -> VB?,
    crossinline viewProvider: (F) -> View = Fragment::requireView
): ViewBindingProperty<F, VB?> = viewBinding { fragment: F -> vbFactory(viewProvider(fragment)) }

private class FragmentViewBindingProperty<F : Fragment, VB : ViewBinding?>(
    viewBinder: (F) -> VB?
) : ViewBindingProperty<F, VB?>(viewBinder) {
    override fun getLifecycleOwner(fragment: F) = fragment.viewLifecycleOwner
}

abstract class ViewBindingProperty<in F : Any, VB : ViewBinding?>(
    private val viewBinder: (F) -> VB?
) : ReadOnlyProperty<F, VB?> {

    private var viewBinding: VB? = null
    private val lifecycleObserver = ClearOnDestroyLifecycleObserver()
    private var fragmentRef: F? = null

    protected abstract fun getLifecycleOwner(fragment: F): LifecycleOwner

    @MainThread
    override fun getValue(thisRef: F, property: KProperty<*>): VB? {
        checkIsMainThread()
        viewBinding?.let { return it }

        this.fragmentRef = thisRef
        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        when (lifecycle.currentState) {
            Lifecycle.State.DESTROYED -> mainHandler.post { viewBinding = null }
            else -> lifecycle.addObserver(lifecycleObserver)
        }
        return viewBinder(thisRef).also { viewBinding = it }
    }

    @MainThread
    fun clear() {
        checkIsMainThread()

        val fragmentRef = fragmentRef ?: return
        this.fragmentRef = null
        getLifecycleOwner(fragmentRef).lifecycle.removeObserver(lifecycleObserver)
        mainHandler.post { viewBinding = null }
    }

    private inner class ClearOnDestroyLifecycleObserver : DefaultLifecycleObserver {
        @MainThread
        override fun onDestroy(owner: LifecycleOwner): Unit = clear()
    }

    private companion object {
        private val mainHandler = Handler(Looper.getMainLooper())
    }
}