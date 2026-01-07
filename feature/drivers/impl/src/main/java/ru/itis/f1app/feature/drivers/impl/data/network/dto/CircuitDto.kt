package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CircuitDto(
    @SerialName("circuitId") val circuitId: String,
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("city") val city: String,
    @SerialName("length") val length: Int,
    @SerialName("lapRecord") val lapRecord: String,
    @SerialName("firstParticipationYear") val firstParticipationYear: Int,
    @SerialName("numberOfCorners") val numberOfCorners: Int,
    @SerialName("fastestLapDriverId") val fastestLapDriverId: String,
    @SerialName("fastestLapTeamId") val fastestLapTeamId: String,
    @SerialName("fastestLapYear") val fastestLapYear: Int
)
