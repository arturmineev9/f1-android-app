package ru.itis.f1app.feature.standings.impl.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding
import ru.itis.f1app.feature.standings.api.domain.repository.StandingsRepository

class GetDriverStandingsUseCaseImplTest {

    private val repository: StandingsRepository = mockk()
    private val useCase = GetDriverStandingsUseCaseImpl(repository)

    @Test
    fun `invoke calls repository with correct year`() = runTest {
        val year = "2025"
        val expectedData = listOf(mockk<DriverStanding>())
        coEvery { repository.getDriverStandings(year) } returns flowOf(expectedData)

        val result = useCase(year).toList().first()

        assertEquals(expectedData, result)
        coVerify(exactly = 1) { repository.getDriverStandings(year) }
    }
}
