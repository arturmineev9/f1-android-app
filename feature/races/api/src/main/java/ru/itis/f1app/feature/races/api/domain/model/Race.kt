package ru.itis.f1app.feature.races.api.domain.model

data class Race(
    val id: String,
    val name: String,
    val country: String,
    val city: String,
    val circuitName: String,
    val dateStart: String,
    val round: Int
)
