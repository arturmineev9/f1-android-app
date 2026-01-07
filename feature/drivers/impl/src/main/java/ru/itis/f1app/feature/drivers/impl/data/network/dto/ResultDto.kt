package ru.itis.f1app.feature.drivers.impl.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultDto(
    @SerialName("finishingPosition") val finishingPosition: String? = null,
    @SerialName("gridPosition") val gridPosition: String? = null,
    @SerialName("raceTime") val raceTime: String? = null,
    @SerialName("pointsObtained") val pointsObtained: Double? = null,
    @SerialName("retired") val retired: Boolean? = null
)
