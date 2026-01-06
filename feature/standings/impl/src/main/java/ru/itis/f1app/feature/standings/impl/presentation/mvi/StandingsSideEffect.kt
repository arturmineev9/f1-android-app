package ru.itis.f1app.feature.standings.impl.presentation.mvi

sealed interface StandingsSideEffect {
    data class NavigateToDriverDetails(val driverId: String) : StandingsSideEffect
    data class ShowError(val message: String) : StandingsSideEffect
}
