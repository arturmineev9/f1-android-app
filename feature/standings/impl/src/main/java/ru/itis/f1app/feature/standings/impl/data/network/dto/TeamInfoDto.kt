package ru.itis.f1app.feature.standings.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamInfoDto(
    @SerialName("teamName") val name: String,
    @SerialName("country") val country: String
)
