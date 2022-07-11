package com.dolgalyovviktor.superdogroceries.app.di

import com.dolgalyovviktor.superdogroceries.app.SuperdoApp
import com.dolgalyovviktor.superdogroceries.data.di.ProductDataModule
import com.dolgalyovviktor.superdogroceries.feature.container.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ProductDataModule::class
    ]
)
interface AppComponent {

    fun inject(target: SuperdoApp)

    fun plusMain(): MainComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance target: SuperdoApp): AppComponent
    }
}