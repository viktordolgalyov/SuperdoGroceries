package com.dolgalyovviktor.superdogroceries.feature.product_list.view

import androidx.recyclerview.widget.DiffUtil
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListItem

class ProductListItemCallback : DiffUtil.ItemCallback<ProductListItem>() {
    override fun areItemsTheSame(
        oldItem: ProductListItem,
        newItem: ProductListItem
    ): Boolean = when (oldItem) {
        is ProductListItem.Product -> newItem is ProductListItem.Product && oldItem.model.id == newItem.model.id
        is ProductListItem.Progress -> newItem is ProductListItem.Progress && oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ProductListItem,
        newItem: ProductListItem
    ): Boolean = when (oldItem) {
        is ProductListItem.Product -> newItem is ProductListItem.Product && oldItem.model == newItem.model
        is ProductListItem.Progress -> newItem is ProductListItem.Progress && oldItem == newItem
    }
}