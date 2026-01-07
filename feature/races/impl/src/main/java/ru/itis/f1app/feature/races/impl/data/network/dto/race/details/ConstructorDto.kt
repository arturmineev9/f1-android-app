package ru.itis.f1app.feature.races.impl.data.network.dto.race.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorDto(
    @SerialName("teamId")val teamId: String,
    @SerialName("url")val url: String,
    @SerialName("teamName") val name: String,
    @SerialName("nationality")val nationality: String
)
