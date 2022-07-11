package com.dolgalyovviktor.superdogroceries.feature.product_list.di

import com.dolgalyovviktor.superdogroceries.common.arch.Dispatchers
import com.dolgalyovviktor.superdogroceries.common.arch.FlowScope
import com.dolgalyovviktor.superdogroceries.domain.ProductService
import com.dolgalyovviktor.superdogroceries.feature.container.router.MainRouter
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListViewModelFactory
import com.dolgalyovviktor.superdogroceries.feature.product_list.router.ProductListCiceroneRouter
import com.dolgalyovviktor.superdogroceries.feature.product_list.router.ProductListRouter
import dagger.Module
import dagger.Provides

@Module
class ProductListModule {

    @Provides
    @FlowScope
    fun router(
        parentRouter: MainRouter
    ): ProductListRouter = ProductListCiceroneRouter(
        parentRouter = parentRouter
    )

    @Provides
    @FlowScope
    fun viewModelFactory(
        productService: ProductService,
        dispatchers: Dispatchers
    ) = ProductListViewModelFactory(
        productService = productService,
        dispatchers = dispatchers
    )
}