package com.dolgalyovviktor.superdogroceries.domain

import com.dolgalyovviktor.superdogroceries.data.ProductRepository

class ProductService(
    private val productRepository: ProductRepository
) {

    fun observeProducts() = productRepository.observeProducts()
}