package ru.itis.f1app.feature.standings.impl.data.network.datasource

import ru.itis.f1app.feature.standings.impl.data.network.api.StandingsApi
import ru.itis.f1app.feature.standings.impl.data.network.dto.ConstructorStandingDto
import ru.itis.f1app.feature.standings.impl.data.network.dto.DriverStandingDto
import javax.inject.Inject

class StandingsRemoteDataSourceImpl @Inject constructor(
    private val api: StandingsApi
) : StandingsRemoteDataSource {

    override suspend fun getDriverStandings(year: String): List<DriverStandingDto> {
        return api.getDriverStandings(year).standings
    }

    override suspend fun getConstructorStandings(year: String): List<ConstructorStandingDto> {
        return api.getConstructorStandings(year).standings
    }
}
