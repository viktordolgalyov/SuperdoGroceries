package com.dolgalyovviktor.superdogroceries.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//{"bagColor": "#F191BA", "name": "Peas", "weight": "0.7kg"}
@Serializable
data class SuperdoProduct(
    @SerialName("name") val name: String,
    @SerialName("bagColor") val color: String,
    @SerialName("weight") val weight: String
)