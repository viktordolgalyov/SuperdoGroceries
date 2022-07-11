package com.dolgalyovviktor.superdogroceries.feature.product_list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dolgalyovviktor.superdogroceries.R
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListItem

class ProductListAdapter :
    ListAdapter<ProductListItem, RecyclerView.ViewHolder>(ProductListItemCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_product_item -> ProductItemHolder(
                containerView = view
            )
            R.layout.item_product_progress -> ProductItemProgressHolder(
                containerView = view
            )
            else -> throw IllegalArgumentException("Item type is not registered")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) = when (val item = getItem(position)) {
        is ProductListItem.Product -> (holder as ProductItemHolder).render(item)
        is ProductListItem.Progress -> Unit
    }

    override fun getItemId(position: Int): Long = when (val item = getItem(position)) {
        is ProductListItem.Product -> item.model.id.hashCode().toLong()
        is ProductListItem.Progress -> item.id.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ProductListItem.Product -> R.layout.item_product_item
        is ProductListItem.Progress -> R.layout.item_product_progress
    }
}