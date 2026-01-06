package ru.itis.f1app.feature.races.impl.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions
import ru.itis.f1app.feature.races.api.domain.model.RaceDetails
import ru.itis.f1app.feature.races.api.domain.repository.RacesRepository

class GetRaceDetailsUseCaseTest {

    private val repository: RacesRepository = mockk()

    private val useCase = GetRaceDetailsUseCaseImpl(repository)

    @Test
    fun `invoke calls repository with correct parameters and returns data`() = runTest {
        val year = 2024
        val round = 5
        val expectedDetails = mockk<RaceDetails>()

        coEvery { repository.getRaceDetails(year, round) } returns expectedDetails

        val result = useCase(year, round)

        assertEquals(expectedDetails, result)
        coVerify(exactly = 1) { repository.getRaceDetails(year, round) }
    }

    @Test(expected = RacesExceptions.NetworkConnection::class)
    fun `invoke throws exception when repository fails`() = runTest {
        val year = 2024
        val round = 5

        coEvery { repository.getRaceDetails(year, round) } throws RacesExceptions.NetworkConnection(Exception())

        useCase(year, round)
    }
}
