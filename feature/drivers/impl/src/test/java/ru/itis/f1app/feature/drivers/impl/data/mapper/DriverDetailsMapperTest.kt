package ru.itis.f1app.feature.drivers.impl.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.drivers.impl.data.network.dto.DriverDetailsResponseDto
import ru.itis.f1app.feature.drivers.impl.data.network.dto.DriverDto
import ru.itis.f1app.feature.drivers.impl.data.network.dto.TeamDto

class DriverDetailsMapperTest {

    private val mapper = DriverDetailsMapper()

    @Test
    fun `DriverDetailsResponseDto toDomain maps correctly`() {
        val dto = DriverDetailsResponseDto(
            driver = DriverDto(
                driverId = "alonso",
                name = "Fernando",
                surname = "Alonso",
                nationality = "Spain",
                birthday = "29/07/1981",
                number = 14,
                url = "https://en.wikipedia.org/wiki/Fernando_Alonso"
            ),
            team = TeamDto(
                teamId = "aston_martin",
                teamName = "Aston Martin F1 Team",
                teamNationality = "Great Britain",
                firstAppeareance = 1959,
                constructorsChampionships = null,
                driversChampionships = null,
                url = "https://en.wikipedia.org/wiki/Aston_Martin"
            ),
            results = emptyList()
        )
        val domain = mapper.mapDriverDetails(dto)

        assertEquals("alonso", domain.id)
        assertEquals("Fernando Alonso", domain.fullName)
        assertEquals("14", domain.number)
        assertEquals("Spain", domain.nationality)
        assertEquals("29/07/1981", domain.birthDate)
        assertEquals("Aston Martin F1 Team", domain.teamName)
        assertEquals("aston_martin", domain.teamId)
    }
}
