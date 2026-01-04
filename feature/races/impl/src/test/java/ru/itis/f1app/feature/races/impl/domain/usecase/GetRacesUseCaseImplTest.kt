package ru.itis.f1app.feature.races.impl.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.api.domain.repository.RacesRepository

class GetRacesUseCaseImplTest {

    private val repository: RacesRepository = mockk()
    private val useCase = GetRacesUseCaseImpl(repository)

    @Test
    fun `invoke calls repository getRaces`() {
        val year = 2024
        val expectedFlow = flowOf(listOf<Race>())
        every { repository.getRaces(year) } returns expectedFlow

        val result = useCase(year)

        assertEquals(expectedFlow, result)
        verify(exactly = 1) { repository.getRaces(year) }
    }
}
