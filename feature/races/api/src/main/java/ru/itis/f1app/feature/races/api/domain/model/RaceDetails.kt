package ru.itis.f1app.feature.races.api.domain.model

data class RaceDetails(
    val raceId: String,
    val raceName: String,
    val circuitName: String,
    val location: String,
    val date: String,
    val results: List<RaceResult>
)

data class RaceResult(
    val position: Int,
    val driverId: String,
    val driverName: String,
    val constructorName: String,
    val points: String,
    val time: String,
    val gridPosition: Int
)
