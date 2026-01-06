package ru.itis.f1app.feature.standings.api.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding

interface StandingsRepository {
    fun getDriverStandings(year: String): Flow<List<DriverStanding>>
    fun getConstructorStandings(year: String): Flow<List<ConstructorStanding>>
}
