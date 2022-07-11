package com.dolgalyovviktor.superdogroceries.feature.container.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.Dispatchers
import com.dolgalyovviktor.superdogroceries.common.arch.ReduxViewModel
import com.dolgalyovviktor.superdogroceries.common.arch.exec
import com.dolgalyovviktor.superdogroceries.feature.container.router.MainRouter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MainViewModel(
    reducer: MainReducer,
    mapper: MainStateModelMapper,
    dispatchers: Dispatchers
) : ReduxViewModel<MainAction, MainChange, MainState, MainPresentationModel, MainRouter>(
    reducer = reducer,
    stateToModelMapper = mapper,
    dispatchers = dispatchers
) {
    override var state = MainState

    override fun onObserverActive(firstTime: Boolean) {
        super.onObserverActive(firstTime)
        if (firstTime) router.exec { this.openProductList() }
    }

    override suspend fun provideChangesObservable(): Flow<MainChange> = emptyFlow()

    override fun processAction(action: MainAction) = Unit
}