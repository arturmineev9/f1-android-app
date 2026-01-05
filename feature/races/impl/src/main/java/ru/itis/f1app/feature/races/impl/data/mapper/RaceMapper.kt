package ru.itis.f1app.feature.races.impl.data.mapper

import ru.itis.f1app.core.database.entity.RaceEntity
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.impl.data.network.dto.race.RaceDto
import javax.inject.Inject

class RaceMapper @Inject constructor() {

    fun mapDtoToEntity(dto: RaceDto, year: Int): RaceEntity {
        return RaceEntity(
            id = dto.id,
            name = dto.name,
            country = dto.circuit.country,
            city = dto.circuit.city,
            circuitName = dto.circuit.name,
            dateStart = "${dto.schedule.race.date}T${dto.schedule.race.time}",
            round = dto.round,
            year = year
        )
    }

    fun mapEntityToDomain(entity: RaceEntity): Race {
        return Race(
            id = entity.id,
            name = entity.name,
            country = entity.country,
            city = entity.city,
            circuitName = entity.circuitName,
            dateStart = entity.dateStart,
            round = entity.round
        )
    }

    fun mapDtoListToEntityList(dtos: List<RaceDto>, year: Int): List<RaceEntity> {
        return dtos.map { mapDtoToEntity(it, year) }
    }

    fun mapEntityListToDomainList(entities: List<RaceEntity>): List<Race> {
        return entities.map { mapEntityToDomain(it) }
    }
}
