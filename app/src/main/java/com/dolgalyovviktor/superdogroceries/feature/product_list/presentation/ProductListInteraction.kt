package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.UIAction
import com.dolgalyovviktor.superdogroceries.common.arch.UIEvent
import com.dolgalyovviktor.superdogroceries.common.arch.UIStateChange
import com.dolgalyovviktor.superdogroceries.domain.model.Product

sealed class ProductListAction : UIAction {
    object ChangeListeningStateClick : ProductListAction()
}

sealed class ProductListChange : UIStateChange {
    data class ProductsAdded(val data: List<Product>) : ProductListChange()
    data class ListeningStateChanged(val isListening: Boolean) : ProductListChange()
    object ProductsCleared : ProductListChange()
}

sealed class ProductListEvent : UIEvent