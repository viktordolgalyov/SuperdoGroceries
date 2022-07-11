package com.dolgalyovviktor.superdogroceries.feature.container

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dolgalyovviktor.superdogroceries.R
import com.dolgalyovviktor.superdogroceries.common.arch.Navigation
import com.dolgalyovviktor.superdogroceries.common.arch.UIEvent
import com.dolgalyovviktor.superdogroceries.feature.container.di.MainComponent
import com.dolgalyovviktor.superdogroceries.feature.container.presentation.MainPresentationModel
import com.dolgalyovviktor.superdogroceries.feature.container.presentation.MainViewModel
import com.dolgalyovviktor.superdogroceries.feature.container.presentation.MainViewModelFactory
import com.dolgalyovviktor.superdogroceries.feature.container.router.MainRouter
import com.dolgalyovviktor.superdogroceries.feature.product_list.di.ProductListComponent
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ProductListComponent.ComponentProvider {
    private val component by lazy {
        (application as MainComponent.ComponentProvider).provideMainComponent(this)
    }
    @Inject lateinit var router: MainRouter
    @Inject @field:Navigation.Activity lateinit var navigationHolder: NavigatorHolder
    @Inject @field:Navigation.Activity lateinit var navigator: Navigator
    @Inject lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)
        viewModel.router = router
        viewModel.observableModel.observe(this, Observer(::renderModel))
        viewModel.observableEvent.observe(this, Observer(::renderEvent))
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigationHolder.removeNavigator()
    }

    private fun renderModel(@Suppress("UNUSED_PARAMETER") model: MainPresentationModel) = Unit

    private fun renderEvent(@Suppress("UNUSED_PARAMETER") event: UIEvent) = Unit

    override fun provideProductListComponent(): ProductListComponent = component.plusProductList().create()
}