package com.dolgalyovviktor.superdogroceries.data.mapper

import com.dolgalyovviktor.superdogroceries.data.remote.model.SuperdoProduct
import com.dolgalyovviktor.superdogroceries.domain.model.Product

class ProductMapper {

    fun map(from: SuperdoProduct) = Product(
        id = "${from.name}${from.color}${from.weight}",
        name = from.name,
        color = from.color,
        quantity = from.weight
    )
}