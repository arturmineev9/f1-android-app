package ru.itis.f1app.feature.races.impl.data.network.dto.race.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CircuitDto(
    @SerialName("circuitId") val circuitId: String,
    @SerialName("url") val url: String,
    @SerialName("circuitName") val circuitName: String,
    @SerialName("city") val locality: String,
    @SerialName("country") val country: String
)
