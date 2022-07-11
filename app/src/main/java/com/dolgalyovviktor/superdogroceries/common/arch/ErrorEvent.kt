package com.dolgalyovviktor.superdogroceries.common.arch

interface ErrorEvent : UIEvent {
    object SomethingWrongEvent : ErrorEvent
    data class ErrorMessageEvent(val message: String) : ErrorEvent
}