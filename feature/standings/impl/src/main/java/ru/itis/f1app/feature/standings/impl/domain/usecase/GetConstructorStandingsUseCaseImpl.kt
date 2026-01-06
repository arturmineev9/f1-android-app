package ru.itis.f1app.feature.standings.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding
import ru.itis.f1app.feature.standings.api.domain.repository.StandingsRepository
import ru.itis.f1app.feature.standings.api.domain.usecase.GetConstructorStandingsUseCase
import javax.inject.Inject

class GetConstructorStandingsUseCaseImpl @Inject constructor(
    private val repository: StandingsRepository
) : GetConstructorStandingsUseCase {
    override operator fun invoke(year: String): Flow<List<ConstructorStanding>> {
        return repository.getConstructorStandings(year)
    }
}
