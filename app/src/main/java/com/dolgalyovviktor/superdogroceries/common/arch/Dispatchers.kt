package com.dolgalyovviktor.superdogroceries.common.arch

import kotlinx.coroutines.CoroutineDispatcher

data class Dispatchers(
    val background: CoroutineDispatcher,
    val main: CoroutineDispatcher
)