package ru.itis.f1app.feature.drivers.impl.data.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.drivers.impl.data.mapper.DriverDetailsMapper
import ru.itis.f1app.feature.drivers.impl.data.network.datasource.DriversRemoteDataSource
import ru.itis.f1app.feature.drivers.impl.data.network.dto.DriverDetailsResponseDto
import ru.itis.f1app.feature.drivers.impl.data.network.repository.DriversRepositoryImpl
import ru.itis.f1app.feature.drivers.impl.domain.exception.DriversExceptions

class DriversRepositoryImplTest {

    private val remoteDataSource: DriversRemoteDataSource = mockk()
    private val mapper = DriverDetailsMapper()
    private val repository = DriversRepositoryImpl(remoteDataSource, mapper)

    @Test
    fun `getDriverDetails emits mapped data on success`() = runTest {
        val mockResponse = mockk<DriverDetailsResponseDto>(relaxed = true)
        coEvery { remoteDataSource.getDriverDetails("2025", "alonso") } returns mockResponse

        val result = repository.getDriverDetails("2025", "alonso").first()

        assertEquals(mockResponse.driver.driverId, result.id)
    }

    @Test(expected = DriversExceptions.NoDataAvailable::class)
    fun `getDriverDetails throws NoDataAvailable when empty response`() = runTest {
        coEvery { remoteDataSource.getDriverDetails(any(), any()) } throws DriversExceptions.NoDataAvailable()
        repository.getDriverDetails("2025", "alonso").first()
    }
}
