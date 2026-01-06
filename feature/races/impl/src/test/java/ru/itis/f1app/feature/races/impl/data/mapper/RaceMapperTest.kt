package ru.itis.f1app.feature.races.impl.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.races.impl.data.network.dto.race.CircuitDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.RaceDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.ScheduleDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.SessionDto

// Импорты для деталей
import ru.itis.f1app.feature.races.impl.data.network.dto.race.details.RaceDetailsDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.details.CircuitDto as DetailsCircuitDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.details.ResultDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.details.DriverDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.details.ConstructorDto

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

    @Test
    fun `mapDetailsDtoToDomain maps fields correctly`() {

        val detailsDto = RaceDetailsDto(
            round = "1",
            date = "2025-03-02",
            time = "15:00:00Z",
            raceName = "Bahrain GP",
            url = "http://...",
            circuit = DetailsCircuitDto(
                circuitId = "bahrain",
                url = "...",
                circuitName = "Sakhir Circuit",
                locality = "Sakhir",
                country = "Bahrain"
            ),
            results = listOf(
                ResultDto(
                    position = "1",
                    points = 25.0,
                    grid = "1",
                    time = "1:30:00",
                    driver = DriverDto(
                        driverId = "max",
                        url = "...",
                        givenName = "Max",
                        familyName = "Verstappen",
                        nationality = "Dutch"
                    ),
                    team = ConstructorDto(
                        teamId = "rb",
                        url = "...",
                        name = "Red Bull",
                        nationality = "Austrian"
                    )
                )
            )
        )

        val domain = mapper.mapDetailsDtoToDomain(detailsDto)

        assertEquals("Bahrain GP", domain.raceName)
        assertEquals("Sakhir Circuit", domain.circuitName)
        assertEquals("Sakhir, Bahrain", domain.location)
        assertEquals(1, domain.results.size)

        val firstResult = domain.results[0]
        assertEquals("Max Verstappen", firstResult.driverName)
        assertEquals(1, firstResult.position)
        assertEquals("25.0", firstResult.points)
    }
}
