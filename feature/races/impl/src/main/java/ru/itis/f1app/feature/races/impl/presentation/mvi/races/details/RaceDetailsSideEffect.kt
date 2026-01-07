package ru.itis.f1app.feature.races.impl.presentation.mvi.races.details

sealed interface RaceDetailsSideEffect {
    data object NavigateBack : RaceDetailsSideEffect
    data class NavigateToDriverDetails(val driverId: String) : RaceDetailsSideEffect
}
