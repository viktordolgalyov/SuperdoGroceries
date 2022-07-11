package com.dolgalyovviktor.superdogroceries.common.arch

import java.util.concurrent.CancellationException
import kotlin.reflect.KClass

open class ErrorHandler(private val viewProvider: () -> ErrorView?) {
    open val excludedErrors = emptySet<KClass<out Throwable>>()
    open val handleUnknown = true

    fun startErrorHandling(error: Throwable) {
        onHandlingStarted(error)

        val isHandled = when {
            isErrorIgnored(error) -> false
            handleError(error) -> true
            error is CancellationException -> true
            else -> handleUnknownError()
        }
        onHandlingFinished(error, isHandled)
    }

    open fun handleError(error: Throwable): Boolean = false

    open fun onHandlingStarted(error: Throwable) {}

    open fun onHandlingFinished(error: Throwable, isHandled: Boolean) {}

    private fun isErrorIgnored(error: Throwable) = excludedErrors.contains(error::class)

    private fun handleUnknownError(): Boolean {
        return if (handleUnknown) {
            viewProvider()?.showSomethingGoesWrong()
            true
        } else {
            false
        }
    }
}