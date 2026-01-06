package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceDto(
    @SerialName("raceId") val raceId: String,
    @SerialName("name") val name: String,
    @SerialName("round") val round: Int,
    @SerialName("date") val date: String,
    @SerialName("circuit") val circuit: CircuitDto
)
