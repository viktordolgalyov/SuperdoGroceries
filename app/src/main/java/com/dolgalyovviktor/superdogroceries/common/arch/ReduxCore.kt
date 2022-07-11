package com.dolgalyovviktor.superdogroceries.common.arch

interface UIAction : ReduxLoggable

interface UIStateChange : ReduxLoggable

interface UIState : ReduxLoggable

interface UIModel : ReduxLoggable

interface UIEvent : ReduxLoggable

interface UIRouter

interface Reducer<S : UIState, C : UIStateChange> {
    fun reduce(state: S, change: C): S
}

interface StateToModelMapper<S : UIState, M : UIModel> {
    fun mapStateToModel(state: S): M
}

interface ReduxLoggable {
    fun isLoggable(): Boolean = true

    fun log(): String = toString()
}