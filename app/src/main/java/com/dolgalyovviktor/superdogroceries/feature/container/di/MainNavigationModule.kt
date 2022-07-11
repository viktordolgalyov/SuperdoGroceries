package com.dolgalyovviktor.superdogroceries.feature.container.di

import com.dolgalyovviktor.superdogroceries.R
import com.dolgalyovviktor.superdogroceries.common.arch.ActivityScope
import com.dolgalyovviktor.superdogroceries.common.arch.Navigation
import com.dolgalyovviktor.superdogroceries.feature.container.MainActivity
import com.dolgalyovviktor.superdogroceries.feature.container.navigation.MainNavigator
import com.dolgalyovviktor.superdogroceries.feature.container.router.MainCiceroneRouter
import com.dolgalyovviktor.superdogroceries.feature.container.router.MainRouter
import com.github.terrakok.cicerone.*
import dagger.Module
import dagger.Provides

@Module
class MainNavigationModule {
    private val cicerone = Cicerone.create(Router())

    @Provides
    @ActivityScope
    @Navigation.Activity
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @ActivityScope
    @Navigation.Activity
    fun navigator(
        activity: MainActivity
    ): Navigator = MainNavigator(
        activity = activity,
        fragmentManager = activity.supportFragmentManager,
        containerId = R.id.fragmentContainer
    )

    @Provides
    @ActivityScope
    fun router(): MainRouter = MainCiceroneRouter(
        router = cicerone.router
    )
}