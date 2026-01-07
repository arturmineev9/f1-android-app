package ru.itis.f1app.feature.drivers.impl.presentation.mvi

import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails

data class DriverDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val driverDetails: DriverDetails? = null
)
