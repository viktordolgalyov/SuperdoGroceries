package com.dolgalyovviktor.superdogroceries.feature.container.di

import com.dolgalyovviktor.superdogroceries.common.arch.ActivityScope
import com.dolgalyovviktor.superdogroceries.domain.di.ProductDomainModule
import com.dolgalyovviktor.superdogroceries.feature.container.MainActivity
import com.dolgalyovviktor.superdogroceries.feature.product_list.di.ProductListComponent
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        MainModule::class,
        MainNavigationModule::class,
        ProductDomainModule::class
    ]
)
interface MainComponent {

    fun inject(target: MainActivity)

    fun plusProductList(): ProductListComponent.Factory

    interface ComponentProvider {
        fun provideMainComponent(target: MainActivity): MainComponent
    }

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance target: MainActivity): MainComponent
    }
}