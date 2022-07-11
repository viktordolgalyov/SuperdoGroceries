package com.dolgalyovviktor.superdogroceries.common.ui.util

import android.view.View

inline fun View.doOnNextLayout(
    crossinline action: () -> Unit,
    crossinline condition: () -> Boolean
) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            if (condition()) {
                action()
                removeOnLayoutChangeListener(this)
            }
        }
    })
}