package com.dolgalyovviktor.superdogroceries.data.di

import com.dolgalyovviktor.superdogroceries.data.ProductRepository
import com.dolgalyovviktor.superdogroceries.data.mapper.ProductMapper
import com.dolgalyovviktor.superdogroceries.data.remote.ProductRemoteSource
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import javax.inject.Singleton

@Module
class ProductDataModule {

    @Provides
    @Singleton
    fun productRemoteSource(
        client: HttpClient
    ) = ProductRemoteSource(
        client = client
    )

    @Provides
    @Singleton
    fun productMapper() = ProductMapper()

    @Provides
    @Singleton
    fun productRepository(
        remoteSource: ProductRemoteSource,
        productMapper: ProductMapper
    ) = ProductRepository(
        remoteSource = remoteSource,
        mapper = productMapper
    )
}