package ru.itis.f1app.feature.races.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.api.domain.repository.RacesRepository
import ru.itis.f1app.feature.races.api.domain.usecase.GetRacesUseCase
import javax.inject.Inject

class GetRacesUseCaseImpl @Inject constructor(
    private val repository: RacesRepository
) : GetRacesUseCase {

    override operator fun invoke(year: Int): Flow<List<Race>> {
        return repository.getRaces(year)
    }
}
