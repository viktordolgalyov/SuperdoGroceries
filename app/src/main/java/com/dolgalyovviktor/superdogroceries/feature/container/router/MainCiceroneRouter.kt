package com.dolgalyovviktor.superdogroceries.feature.container.router

import com.dolgalyovviktor.superdogroceries.feature.container.navigation.Screens
import com.github.terrakok.cicerone.Router

class MainCiceroneRouter(
    private val router: Router
) : MainRouter {

    override fun openProductList() = router.newRootScreen(
        Screens.ProductList()
    )
}