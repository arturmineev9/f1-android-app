package ru.itis.f1app.feature.races.impl.data.network.dto.race.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverDto(
    @SerialName("driverId") val driverId: String,
    @SerialName("code") val code: String? = null,
    @SerialName("url") val url: String,
    @SerialName("name") val givenName: String,
    @SerialName("surname") val familyName: String,
    @SerialName("dateOfBirth") val dateOfBirth: String? = null,
    @SerialName("nationality") val nationality: String
)
