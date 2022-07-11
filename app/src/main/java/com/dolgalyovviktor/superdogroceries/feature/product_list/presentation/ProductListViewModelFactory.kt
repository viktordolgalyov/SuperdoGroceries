package com.dolgalyovviktor.superdogroceries.feature.product_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dolgalyovviktor.superdogroceries.common.arch.Dispatchers
import com.dolgalyovviktor.superdogroceries.domain.ProductService

class ProductListViewModelFactory(
    private val productService: ProductService,
    private val dispatchers: Dispatchers
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ProductListViewModel(
        productService = productService,
        reducer = ProductListReducer(),
        mapper = ProductListStateModelMapper(),
        dispatchers = dispatchers
    ) as T
}