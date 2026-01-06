package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultDto(
    @SerialName("finishingPosition") val finishingPosition: Int,
    @SerialName("gridPosition") val gridPosition: Int,
    @SerialName("raceTime") val raceTime: String,
    @SerialName("pointsObtained") val pointsObtained: Double,
    @SerialName("retired") val retired: Boolean? = null
)
