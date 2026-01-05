package ru.itis.f1app.feature.races.impl.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.races.impl.data.network.dto.race.CircuitDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.RaceDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.ScheduleDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.SessionDto

class RaceMapperTest {

    private val mapper = RaceMapper()

    @Test
    fun `mapDtoToEntity maps fields correctly and concatenates date`() {
        val year = 2025
        val dto = RaceDto(
            id = "bahrain_2025",
            name = "Bahrain GP",
            round = 1,
            circuit = CircuitDto(
                name = "Sakhir",
                country = "Bahrain",
                city = "Sakhir City"
            ),
            schedule = ScheduleDto(
                race = SessionDto(
                    date = "2025-03-02",
                    time = "15:00:00Z"
                )
            )
        )

        val entity = mapper.mapDtoToEntity(dto, year)

        assertEquals("bahrain_2025", entity.id)
        assertEquals("Bahrain GP", entity.name)
        assertEquals("Bahrain", entity.country)
        assertEquals("Sakhir", entity.circuitName)
        assertEquals(1, entity.round)
        assertEquals(2025, entity.year)
        assertEquals("2025-03-02T15:00:00Z", entity.dateStart)
    }
}
