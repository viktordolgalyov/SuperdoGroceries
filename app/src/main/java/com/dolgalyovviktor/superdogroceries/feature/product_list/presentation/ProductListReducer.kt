package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.Reducer

class ProductListReducer : Reducer<ProductListState, ProductListChange> {
    override fun reduce(
        state: ProductListState,
        change: ProductListChange
    ): ProductListState = when (change) {
        is ProductListChange.ListeningStateChanged -> state.copy(
            isListening = change.isListening
        )
        is ProductListChange.ProductsAdded -> state.copy(
            items = (state.items + change.data).distinctBy { it.id }
        )
        ProductListChange.ProductsCleared -> state.copy(
            items = emptyList()
        )
    }
}