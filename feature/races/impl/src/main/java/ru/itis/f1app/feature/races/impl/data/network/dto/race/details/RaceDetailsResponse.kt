package ru.itis.f1app.feature.races.impl.data.network.dto.race.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceDetailsResponse(
    @SerialName("races") val race: RaceDetailsDto
)
