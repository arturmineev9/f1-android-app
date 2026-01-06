package ru.itis.f1app.feature.standings.impl.data.mapper

import ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding
import ru.itis.f1app.feature.standings.impl.data.network.dto.ConstructorStandingDto
import ru.itis.f1app.feature.standings.impl.data.network.dto.DriverStandingDto
import javax.inject.Inject

class StandingsMapper @Inject constructor() {
    fun DriverStandingDto.toDomain() = DriverStanding(
        position = position,
        points = points,
        wins = wins,
        driverId = driverId,
        driverName = driver.name,
        driverSurname = driver.surname,
        driverNumber = driver.number ?: 0,
        teamName = team.name,
        teamId = team.name.lowercase().replace(" ", "-")
    )

    fun ConstructorStandingDto.toDomain() = ConstructorStanding(
        position = position,
        points = points,
        wins = wins,
        teamId = teamId,
        teamName = team.name,
        teamNationality = team.country
    )
}
