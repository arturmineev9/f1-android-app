package ru.itis.f1app.feature.standings.api.domain.model

data class DriverStanding(
    val position: Int,
    val points: Double,
    val wins: Int,
    val driverId: String,
    val driverName: String,
    val driverSurname: String,
    val driverNumber: Int,
    val teamName: String,
    val teamId: String
)
