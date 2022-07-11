package com.dolgalyovviktor.superdogroceries.feature.container.presentation

import com.dolgalyovviktor.superdogroceries.common.arch.StateToModelMapper

class MainStateModelMapper : StateToModelMapper<MainState, MainPresentationModel> {
    override fun mapStateToModel(state: MainState): MainPresentationModel = MainPresentationModel
}