package com.dolgalyovviktor.superdogroceries.data

import com.dolgalyovviktor.superdogroceries.data.mapper.ProductMapper
import com.dolgalyovviktor.superdogroceries.data.remote.ProductRemoteSource
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val remoteSource: ProductRemoteSource,
    private val mapper: ProductMapper
) {

    fun observeProducts() = remoteSource.observeProducts().map { mapper.map(it) }
}