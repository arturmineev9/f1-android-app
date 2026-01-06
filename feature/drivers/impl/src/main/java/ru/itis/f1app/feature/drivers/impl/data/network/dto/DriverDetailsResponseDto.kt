package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverDetailsResponseDto(
    @SerialName("driver") val driver: DriverDto,
    @SerialName("team") val team: TeamDto,
    @SerialName("results") val results: List<RaceResultDto>
)
