package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.UIModel

data class ProductListPresentationModel(
    val items: List<ProductListItem>,
    val listeningButton: ListeningButtonState
) : UIModel {

    sealed class ListeningButtonState {
        object Start : ListeningButtonState()
        object Stop : ListeningButtonState()
    }
}