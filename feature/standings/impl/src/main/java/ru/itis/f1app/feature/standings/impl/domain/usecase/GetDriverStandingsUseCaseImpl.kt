package ru.itis.f1app.feature.standings.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding
import ru.itis.f1app.feature.standings.api.domain.repository.StandingsRepository
import ru.itis.f1app.feature.standings.api.domain.usecase.GetDriverStandingsUseCase
import javax.inject.Inject

class GetDriverStandingsUseCaseImpl @Inject constructor(
    private val repository: StandingsRepository
) : GetDriverStandingsUseCase {

    override operator fun invoke(year: String): Flow<List<DriverStanding>> {
        return repository.getDriverStandings(year)
    }
}
