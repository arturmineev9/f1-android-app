package ru.itis.f1app.feature.standings.impl.data.network.datasource

import ru.itis.f1app.feature.standings.impl.data.network.dto.ConstructorStandingDto
import ru.itis.f1app.feature.standings.impl.data.network.dto.DriverStandingDto

interface StandingsRemoteDataSource {
    suspend fun getDriverStandings(year: String): List<DriverStandingDto>
    suspend fun getConstructorStandings(year: String): List<ConstructorStandingDto>
}
