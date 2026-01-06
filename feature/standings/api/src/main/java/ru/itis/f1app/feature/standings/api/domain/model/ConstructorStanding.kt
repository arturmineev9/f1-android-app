package ru.itis.f1app.feature.standings.api.domain.model

data class ConstructorStanding(
    val position: Int,
    val points: Double,
    val wins: Int,
    val teamId: String,
    val teamName: String,
    val teamNationality: String
)
