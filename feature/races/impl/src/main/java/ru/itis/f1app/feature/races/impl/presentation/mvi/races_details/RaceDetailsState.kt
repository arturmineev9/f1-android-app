package ru.itis.f1app.feature.races.impl.presentation.mvi.races_details

import ru.itis.f1app.feature.races.api.domain.model.RaceDetails

data class RaceDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val details: RaceDetails? = null
)
