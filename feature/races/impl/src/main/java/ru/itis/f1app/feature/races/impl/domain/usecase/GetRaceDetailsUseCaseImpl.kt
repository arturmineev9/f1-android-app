package ru.itis.f1app.feature.races.impl.domain.usecase

import ru.itis.f1app.feature.races.api.domain.model.RaceDetails
import ru.itis.f1app.feature.races.api.domain.repository.RacesRepository
import ru.itis.f1app.feature.races.api.domain.usecase.GetRaceDetailsUseCase
import javax.inject.Inject

class GetRaceDetailsUseCaseImpl @Inject constructor(
    private val repository: RacesRepository
) : GetRaceDetailsUseCase {

    override suspend operator fun invoke(year: Int, round: Int): RaceDetails {
        return repository.getRaceDetails(year, round)
    }
}
