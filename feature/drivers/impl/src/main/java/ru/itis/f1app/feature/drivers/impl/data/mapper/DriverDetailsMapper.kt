package ru.itis.f1app.feature.drivers.impl.data.mapper

import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails
import ru.itis.f1app.feature.drivers.api.domain.model.DriverRaceResult
import ru.itis.f1app.feature.drivers.impl.data.network.dto.DriverDetailsResponseDto
import javax.inject.Inject

class DriverDetailsMapper @Inject constructor() {
    fun mapDriverDetails(response: DriverDetailsResponseDto): DriverDetails {
        val driver = response.driver
        val team = response.team

        return DriverDetails(
            id = driver.driverId,
            fullName = "${driver.name} ${driver.surname}",
            number = driver.number?.toString(),
            nationality = driver.nationality,
            birthDate = driver.birthday,
            teamName = team?.teamName,
            teamId = team?.teamId,
            wikiUrl = driver.url,
            recentResults = response.results.map { resultDto ->
                DriverRaceResult(
                    raceName = resultDto.race.name,
                    raceRound = resultDto.race.round,
                    date = resultDto.race.date,
                    position = resultDto.result?.finishingPosition?.toString() ?: "DNF",
                    points = resultDto.result?.pointsObtained ?: 0.0
                )
            }
        )
    }
}
