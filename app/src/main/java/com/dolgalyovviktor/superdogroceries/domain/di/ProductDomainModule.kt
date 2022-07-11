package com.dolgalyovviktor.superdogroceries.domain.di

import com.dolgalyovviktor.superdogroceries.data.ProductRepository
import com.dolgalyovviktor.superdogroceries.domain.ProductService
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class ProductDomainModule {

    @Provides
    @Reusable
    fun productService(
        productRepository: ProductRepository
    ) = ProductService(
        productRepository = productRepository
    )
}