package com.dolgalyovviktor.superdogroceries.feature.product_list.di

import com.dolgalyovviktor.superdogroceries.common.arch.FlowScope
import com.dolgalyovviktor.superdogroceries.feature.product_list.ProductListFragment
import dagger.Subcomponent

@FlowScope
@Subcomponent(modules = [ProductListModule::class])
interface ProductListComponent {

    fun inject(target: ProductListFragment)

    interface ComponentProvider {
        fun provideProductListComponent(): ProductListComponent
    }

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductListComponent
    }
}