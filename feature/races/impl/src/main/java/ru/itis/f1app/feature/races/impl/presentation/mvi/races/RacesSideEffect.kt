package ru.itis.f1app.feature.races.impl.presentation.mvi.races

import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions


sealed interface RacesSideEffect {
    data class ShowError(val exception: RacesExceptions) : RacesSideEffect
    data class NavigateToDetails(
        val raceId: String,
        val round: Int,
        val year: Int = 2025
    ) : RacesSideEffect
}
