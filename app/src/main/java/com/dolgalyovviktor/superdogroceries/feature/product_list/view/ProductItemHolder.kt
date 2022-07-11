package com.dolgalyovviktor.superdogroceries.feature.product_list.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dolgalyovviktor.superdogroceries.databinding.ItemProductItemBinding
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListItem

class ProductItemHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {
    private val binding = ItemProductItemBinding.bind(containerView)
    private var item: ProductListItem.Product? = null

    fun render(model: ProductListItem.Product) {
        this.item = model
        binding.productCard.render(model.model)
    }
}