package com.dolgalyovviktor.superdogroceries.feature.container.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dolgalyovviktor.superdogroceries.common.arch.Dispatchers

class MainViewModelFactory(
    private val dispatchers: Dispatchers
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(
            reducer = MainReducer(),
            mapper = MainStateModelMapper(),
            dispatchers = dispatchers
        ) as T
}