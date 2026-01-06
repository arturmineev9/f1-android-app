package ru.itis.f1app.feature.drivers.api.domain.model

data class DriverDetails(
    val id: String,
    val fullName: String,
    val number: String?,
    val nationality: String,
    val birthDate: String,
    val teamName: String?,
    val teamId: String?,
    val wikiUrl: String,
    val recentResults: List<DriverRaceResult>
)

data class DriverRaceResult(
    val raceName: String,
    val raceRound: Int,
    val date: String,
    val position: String,
    val points: Double
)
