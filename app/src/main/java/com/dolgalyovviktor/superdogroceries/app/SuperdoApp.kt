package com.dolgalyovviktor.superdogroceries.app

import android.app.Application
import com.dolgalyovviktor.superdogroceries.app.di.DaggerAppComponent
import com.dolgalyovviktor.superdogroceries.feature.container.MainActivity
import com.dolgalyovviktor.superdogroceries.feature.container.di.MainComponent
import timber.log.Timber

class SuperdoApp : Application(), MainComponent.ComponentProvider {
    private val component by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun provideMainComponent(target: MainActivity): MainComponent =
        component.plusMain().create(target)
}