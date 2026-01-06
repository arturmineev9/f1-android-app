package ru.itis.f1app.feature.races.impl.data.network.dto.race

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceDto(
    @SerialName("raceId") val id: String,
    @SerialName("raceName") val name: String,
    @SerialName("round") val round: Int,
    @SerialName("circuit") val circuit: CircuitDto,
    @SerialName("schedule") val schedule: ScheduleDto
)
