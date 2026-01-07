package ru.itis.f1app.feature.standings.impl.presentation.mvi

import ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding

data class StandingsState(
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val drivers: List<DriverStanding> = emptyList(),
    val constructors: List<ConstructorStanding> = emptyList()
)
