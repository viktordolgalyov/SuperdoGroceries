package com.dolgalyovviktor.superdogroceries.feature.container.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.UIAction
import com.dolgalyovviktor.superdogroceries.common.arch.UIEvent
import com.dolgalyovviktor.superdogroceries.common.arch.UIStateChange

sealed class MainAction : UIAction
sealed class MainChange : UIStateChange
sealed class MainEvent : UIEvent