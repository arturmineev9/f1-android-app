package ru.itis.f1app.feature.races.impl.presentation.mvi.races

import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions
import ru.itis.f1app.feature.races.api.domain.model.Race

data class RacesState(
    val races: List<Race> = emptyList(),
    val isLoading: Boolean = false,
    val error: RacesExceptions? = null,
    val selectedYear: Int = 2025
)
