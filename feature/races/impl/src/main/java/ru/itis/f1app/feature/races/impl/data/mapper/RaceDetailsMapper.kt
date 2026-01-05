package ru.itis.f1app.feature.races.impl.data.mapper

import ru.itis.f1app.feature.races.api.domain.model.RaceDetails
import ru.itis.f1app.feature.races.api.domain.model.RaceResult
import ru.itis.f1app.feature.races.impl.data.network.dto.race_details.RaceDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race_details.ResultDto


fun RaceDto.toDomainDetails(): RaceDetails {
    return RaceDetails(
        raceId = this.date,
        raceName = this.raceName,
        circuitName = this.circuit.circuitName,
        location = "${this.circuit.location.locality}, ${this.circuit.location.country}",
        date = this.date,
        results = this.results?.map { it.toDomain() } ?: emptyList()
    )
}

fun ResultDto.toDomain(): RaceResult {
    return RaceResult(
        position = this.position.toIntOrNull() ?: 0,
        driverName = "${this.driver.givenName} ${this.driver.familyName}",
        constructorName = this.constructor.name,
        points = this.points,
        time = this.time?.time ?: this.status,
        gridPosition = this.grid.toIntOrNull() ?: 0
    )
}
