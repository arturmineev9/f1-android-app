package ru.itis.f1app.feature.races.impl.data.network.dto.race_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceDetailsResponse(
    @SerialName("MRData") val mrData: MrDataDto
)

@Serializable
data class MrDataDto(
    @SerialName("RaceTable") val raceTable: RaceTableDto
)

@Serializable
data class RaceTableDto(
    @SerialName("Races") val races: List<RaceDto>
)

@Serializable
data class RaceDto(
    val raceName: String,
    val date: String,
    @SerialName("Circuit") val circuit: CircuitDto,
    @SerialName("Results") val results: List<ResultDto>? = null
)

@Serializable
data class ResultDto(
    val position: String,
    val points: String,
    val grid: String,
    @SerialName("Driver") val driver: DriverDto,
    @SerialName("Constructor") val constructor: ConstructorDto,
    @SerialName("Time") val time: TimeDto? = null,
    val status: String
)

@Serializable
data class DriverDto(val givenName: String, val familyName: String)
@Serializable
data class ConstructorDto(val name: String)
@Serializable
data class TimeDto(val time: String)
@Serializable
data class CircuitDto(val circuitName: String, @SerialName("Location") val location: LocationDto)
@Serializable
data class LocationDto(val locality: String, val country: String)
