package ru.itis.f1app.feature.standings.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverInfoDto(
    @SerialName("name") val name: String,
    @SerialName("surname") val surname: String,
    @SerialName("number") val number: Int? = 0,
    @SerialName("shortName") val shortName: String? = null,
    @SerialName("nationality") val nationality: String
)
