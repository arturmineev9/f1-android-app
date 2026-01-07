package ru.itis.f1app.feature.drivers.impl.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails
import ru.itis.f1app.feature.drivers.api.domain.repository.DriversRepository

class GetDriverDetailsUseCaseImplTest {

    private val repository: DriversRepository = mockk()
    private val useCase = GetDriverDetailsUseCaseImpl(repository)

    @Test
    fun `invoke calls repository with correct parameters`() = runTest {
        val year = "2025"
        val driverId = "alonso"
        val expectedData = listOf(mockk<DriverDetails>())
        coEvery { repository.getDriverDetails(year, driverId) } returns flowOf(expectedData.first())

        val result = useCase(year, driverId).toList().first()

        assertEquals(expectedData.first(), result)
    }
}
