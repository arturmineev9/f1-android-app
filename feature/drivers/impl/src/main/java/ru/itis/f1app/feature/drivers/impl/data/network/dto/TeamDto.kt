package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    @SerialName("teamId") val teamId: String,
    @SerialName("teamName") val teamName: String,
    @SerialName("teamNationality") val teamNationality: String,
    @SerialName("firstAppeareance") val firstAppeareance: Int? = null,
    @SerialName("constructorsChampionships") val constructorsChampionships: Int? = null,
    @SerialName("driversChampionships") val driversChampionships: Int? = null,
    @SerialName("url") val url: String
)
