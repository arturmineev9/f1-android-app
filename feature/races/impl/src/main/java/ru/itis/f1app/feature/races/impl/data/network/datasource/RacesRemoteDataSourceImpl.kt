package ru.itis.f1app.feature.races.impl.data.network.datasource

import ru.itis.f1app.feature.races.impl.data.network.api.RacesApi
import ru.itis.f1app.feature.races.impl.data.network.dto.race.RacesResponse
import ru.itis.f1app.feature.races.impl.data.network.dto.race.details.RaceDetailsResponse
import javax.inject.Inject

class RacesRemoteDataSourceImpl @Inject constructor(
    private val api: RacesApi
) : RacesRemoteDataSource {

    override suspend fun getRaces(year: Int): RacesResponse {
        return api.getRacesByYear(year)
    }

    override suspend fun getRaceDetails(year: Int, round: Int): RaceDetailsResponse {
        return api.getRaceResults(year, round)
    }
}
