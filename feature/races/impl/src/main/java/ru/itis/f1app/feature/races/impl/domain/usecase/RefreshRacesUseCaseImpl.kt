package ru.itis.f1app.feature.races.impl.domain.usecase

import ru.itis.f1app.feature.races.api.domain.repository.RacesRepository
import ru.itis.f1app.feature.races.api.domain.usecase.RefreshRacesUseCase
import javax.inject.Inject

class RefreshRacesUseCaseImpl @Inject constructor(
    private val repository: RacesRepository
) : RefreshRacesUseCase {

    override suspend operator fun invoke(year: Int) {
        repository.refreshRaces(year)
    }
}
