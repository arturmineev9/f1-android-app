package ru.itis.f1app.feature.races.api.domain.usecase

import ru.itis.f1app.feature.races.api.domain.model.RaceDetails

interface GetRaceDetailsUseCase {
    suspend operator fun invoke(year: Int, round: Int): RaceDetails
}
