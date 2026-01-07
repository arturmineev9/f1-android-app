package ru.itis.f1app.feature.drivers.impl.presentation.mvi

sealed interface DriverDetailsSideEffect {
    data class ShowError(val message: String) : DriverDetailsSideEffect
    data object NavigateBack : DriverDetailsSideEffect
}
