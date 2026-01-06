package ru.itis.f1app.feature.drivers.impl.data.mapper

import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails
import ru.itis.f1app.feature.drivers.api.domain.model.DriverRaceResult
import ru.itis.f1app.feature.drivers.impl.data.network.model.DriverDetailsResponseDto
import javax.inject.Inject

class DriverDetailsMapper @Inject constructor() {

    fun DriverDetailsResponseDto.toDomain(): DriverDetails {
        val driver = driver
        val team = team

        return DriverDetails(
            id = driver.id,
            fullName = "${driver.name} ${driver.surname}",
            number = driver.number?.toString(),
            nationality = driver.nationality,
            birthDate = driver.birthday,
            teamName = team?.name,
            teamId = team?.id,
            wikiUrl = driver.wikiUrl,
            recentResults = results.map { resultDto ->
                DriverRaceResult(
                    raceName = resultDto.race.name,
                    raceRound = resultDto.race.round,
                    date = resultDto.race.date,
                    position = resultDto.result?.position?.toString() ?: "DNF",
                    points = resultDto.result?.points ?: 0.0
                )
            }
        )
    }
}
