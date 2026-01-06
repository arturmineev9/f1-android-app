package ru.itis.f1app.feature.standings.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorStandingsResponseDto(
    @SerialName("constructors_championship")
    val standings: List<ConstructorStandingDto>
)
