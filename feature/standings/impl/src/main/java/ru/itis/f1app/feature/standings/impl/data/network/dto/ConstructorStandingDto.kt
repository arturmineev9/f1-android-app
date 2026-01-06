package ru.itis.f1app.feature.standings.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorStandingDto(
    @SerialName("position") val position: Int,
    @SerialName("points") val points: Double,
    @SerialName("wins") val wins: Int,
    @SerialName("teamId") val teamId: String,
    @SerialName("team") val team: TeamInfoDto
)
