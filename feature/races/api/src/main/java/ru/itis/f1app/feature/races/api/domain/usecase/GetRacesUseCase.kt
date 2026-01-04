package ru.itis.f1app.feature.races.api.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.races.api.domain.model.Race

interface GetRacesUseCase {
    operator fun invoke(year: Int): Flow<List<Race>>
}
