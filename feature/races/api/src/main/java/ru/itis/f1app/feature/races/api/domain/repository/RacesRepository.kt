package ru.itis.f1app.feature.races.api.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.races.api.domain.model.Race

interface RacesRepository {
    fun getRaces(year: Int): Flow<List<Race>>
    suspend fun refreshRaces(year: Int)
}
