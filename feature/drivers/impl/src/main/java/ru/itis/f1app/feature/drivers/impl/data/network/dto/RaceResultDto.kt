package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceResultDto(
    @SerialName("race") val race: RaceDto,
    @SerialName("result") val result: ResultDto? = null,
    @SerialName("sprintResult") val sprintResult: ResultDto? = null
)
