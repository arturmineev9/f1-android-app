package ru.itis.f1app.feature.standings.api.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding

interface GetConstructorStandingsUseCase {
    operator fun invoke(year: String = "current"): Flow<List<ConstructorStanding>>
}
