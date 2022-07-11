package com.dolgalyovviktor.superdogroceries.feature.container.di

import com.dolgalyovviktor.superdogroceries.common.arch.ActivityScope
import com.dolgalyovviktor.superdogroceries.common.arch.Dispatchers
import com.dolgalyovviktor.superdogroceries.feature.container.presentation.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @ActivityScope
    fun viewModelFactory(
        dispatchers: Dispatchers
    ) = MainViewModelFactory(
        dispatchers = dispatchers
    )
}