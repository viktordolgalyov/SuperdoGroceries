package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.StateToModelMapper
import com.dolgalyovviktor.superdogroceries.common.ui.product_card.ProductCardRenderModel
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListPresentationModel.*

class ProductListStateModelMapper :
    StateToModelMapper<ProductListState, ProductListPresentationModel> {

    override fun mapStateToModel(state: ProductListState): ProductListPresentationModel {
        val dataItems = state.items.map {
            ProductListItem.Product(
                model = ProductCardRenderModel.from(it)
            )
        }
        val progressItems = when {
            state.isListening -> listOf(ProductListItem.Progress())
            else -> emptyList()
        }
        val listeningButtonState = when {
            state.isListening -> ListeningButtonState.Stop
            else -> ListeningButtonState.Start
        }
        return ProductListPresentationModel(
            items = dataItems + progressItems,
            listeningButton = listeningButtonState
        )
    }
}