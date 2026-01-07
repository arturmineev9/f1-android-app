package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverDto(
    @SerialName("driverId") val driverId: String,
    @SerialName("name") val name: String,
    @SerialName("surname") val surname: String,
    @SerialName("nationality") val nationality: String,
    @SerialName("birthday") val birthday: String,      // "29/07/1981"
    @SerialName("number") val number: Int? = null,
    @SerialName("shortName") val shortName: String? = null,
    @SerialName("url") val url: String
)
