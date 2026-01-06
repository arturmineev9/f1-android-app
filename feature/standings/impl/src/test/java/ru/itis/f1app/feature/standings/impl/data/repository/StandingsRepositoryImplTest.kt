package ru.itis.f1app.feature.standings.impl.data.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.itis.f1app.feature.standings.impl.data.mapper.StandingsMapper
import ru.itis.f1app.feature.standings.impl.data.network.datasource.StandingsRemoteDataSource
import ru.itis.f1app.feature.standings.impl.data.network.dto.DriverStandingDto
import ru.itis.f1app.feature.standings.impl.domain.exception.StandingsExceptions
import java.io.IOException

class StandingsRepositoryImplTest {

    private val remoteDataSource: StandingsRemoteDataSource = mockk()
    private val mapper = StandingsMapper()
    private val repository = StandingsRepositoryImpl(remoteDataSource, mapper)

    @Test
    fun `getDriverStandings emits mapped data on success`() = runTest {
        val mockDtoList = listOf(mockk<DriverStandingDto>(relaxed = true))
        coEvery { remoteDataSource.getDriverStandings("2025") } returns mockDtoList

        val result = repository.getDriverStandings("2025").first()

        assertEquals(1, result.size)
    }

    @Test(expected = StandingsExceptions.NoDataAvailable::class)
    fun `getDriverStandings throws NoDataAvailable when list is empty`() = runTest {
        coEvery { remoteDataSource.getDriverStandings(any()) } returns emptyList()
        repository.getDriverStandings("2025").first()
    }

    @Test
    fun `getDriverStandings maps IOException to NetworkConnection`() = runTest {
        coEvery { remoteDataSource.getDriverStandings(any()) } throws IOException()

        try {
            repository.getDriverStandings("2025").first()
        } catch (e: Exception) {
            assertTrue(e is StandingsExceptions.NetworkConnection)
        }
    }
}
