package com.dolgalyovviktor.superdogroceries.feature.container.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    @IdRes containerId: Int
) : AppNavigator(activity, containerId, fragmentManager)