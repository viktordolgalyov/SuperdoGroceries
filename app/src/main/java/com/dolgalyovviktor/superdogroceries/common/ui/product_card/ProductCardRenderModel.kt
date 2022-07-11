package com.dolgalyovviktor.superdogroceries.common.ui.product_card

import com.dolgalyovviktor.superdogroceries.domain.model.Product

data class ProductCardRenderModel(
    val id: String,
    val name: String,
    val color: String,
    val quantity: String
) {
    companion object {
        fun from(data: Product) = ProductCardRenderModel(
            id = data.id,
            name = data.name,
            color = data.color,
            quantity = data.quantity
        )
    }
}