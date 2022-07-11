package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import com.dolgalyovviktor.superdogroceries.common.ui.product_card.ProductCardRenderModel
import java.util.*

sealed class ProductListItem {
    data class Product(val model: ProductCardRenderModel) : ProductListItem()

    data class Progress(val id: String) : ProductListItem() {
        companion object {
            operator fun invoke() = Progress(UUID.randomUUID().toString())
        }
    }
}