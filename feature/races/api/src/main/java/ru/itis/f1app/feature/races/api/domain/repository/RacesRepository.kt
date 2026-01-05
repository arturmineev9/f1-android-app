package ru.itis.f1app.feature.races.api.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.api.domain.model.RaceDetails

interface RacesRepository {
    fun getRaces(year: Int): Flow<List<Race>>
    suspend fun refreshRaces(year: Int)

    suspend fun getRaceDetails(year: Int, round: Int): RaceDetails
}
