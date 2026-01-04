package ru.itis.f1app.feature.races.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CircuitDto(
    @SerialName("circuitName") val name: String,
    @SerialName("country") val country: String,
    @SerialName("city") val city: String
)
