package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.UIState
import com.dolgalyovviktor.superdogroceries.domain.model.Product

data class ProductListState(
    val items: List<Product>,
    val isListening: Boolean
) : UIState {
    companion object {
        val EMPTY = ProductListState(
            items = emptyList(),
            isListening = true
        )
    }
}