package ru.itis.f1app.feature.races.impl.data.network.dto.race

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RacesResponse(
    @SerialName("races") val races: List<RaceDto>
)
