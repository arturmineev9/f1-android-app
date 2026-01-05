package ru.itis.f1app.feature.races.impl.data.network.datasource

import ru.itis.f1app.feature.races.impl.data.network.dto.race.RacesResponse
import ru.itis.f1app.feature.races.impl.data.network.dto.race.details.RaceDetailsResponse

interface RacesRemoteDataSource {
    suspend fun getRaces(year: Int): RacesResponse
    suspend fun getRaceDetails(year: Int, round: Int): RaceDetailsResponse
}
