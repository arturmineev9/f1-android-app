package ru.itis.f1app.feature.standings.impl.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.standings.impl.data.network.dto.DriverInfoDto
import ru.itis.f1app.feature.standings.impl.data.network.dto.DriverStandingDto
import ru.itis.f1app.feature.standings.impl.data.network.dto.TeamInfoDto

class StandingsMapperTest {

    private val mapper = StandingsMapper()

    @Test
    fun `DriverStandingDto toDomain maps correctly`() {
        val dto = DriverStandingDto(
            position = 1,
            points = 25.0,
            wins = 1,
            driverId = "max_verstappen",
            driver = DriverInfoDto(
                name = "Max",
                surname = "Verstappen",
                number = 1,
                nationality = "Dutch"
            ),
            team = TeamInfoDto(
                name = "Red Bull Racing",
                country = "Austria"
            )
        )

        val domain = with(mapper) { dto.toDomain() }

        assertEquals(1, domain.position)
        assertEquals(25.0, domain.points, 0.0)
        assertEquals("Max", domain.driverName)
        assertEquals("Verstappen", domain.driverSurname)
        assertEquals("Red Bull Racing", domain.teamName)
        assertEquals("red-bull-racing", domain.teamId)
    }
}
