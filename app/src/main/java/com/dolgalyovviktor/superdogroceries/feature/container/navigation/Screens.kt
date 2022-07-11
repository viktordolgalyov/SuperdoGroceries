package com.dolgalyovviktor.superdogroceries.feature.container.navigation

import com.dolgalyovviktor.superdogroceries.feature.product_list.ProductListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

@Suppress("FunctionName")
object Screens {

    fun ProductList() = FragmentScreen {
        ProductListFragment.create()
    }
}