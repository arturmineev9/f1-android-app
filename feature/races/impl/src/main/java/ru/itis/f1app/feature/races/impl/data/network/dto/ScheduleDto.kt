package ru.itis.f1app.feature.races.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    @SerialName("race") val race: SessionDto
)
