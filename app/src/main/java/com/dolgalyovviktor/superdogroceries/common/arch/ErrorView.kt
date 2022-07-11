package com.dolgalyovviktor.superdogroceries.common.arch

interface ErrorView {
    fun showErrorText(text: String)
    fun showSomethingGoesWrong()
}