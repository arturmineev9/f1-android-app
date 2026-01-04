package ru.itis.f1app.feature.races.impl.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.itis.f1app.core.database.dao.RacesDao
import ru.itis.f1app.core.database.entity.RaceEntity
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.impl.data.mapper.RaceMapper
import ru.itis.f1app.feature.races.impl.data.network.api.RacesApi
import ru.itis.f1app.feature.races.impl.data.network.dto.RaceDto
import ru.itis.f1app.feature.races.impl.data.network.dto.RacesResponseDto
import ru.itis.f1app.feature.races.impl.data.network.repository.RacesRepositoryImpl

class RacesRepositoryImplTest {

    private val api: RacesApi = mockk()
    private val dao: RacesDao = mockk()
    private val mapper: RaceMapper = mockk()

    private val repository = RacesRepositoryImpl(api, dao, mapper)

    @Test
    fun `getRaces returns mapped domain models from dao`() = runTest {
        val year = 2024
        val entityList = listOf(mockk<RaceEntity>())
        val domainList = listOf(mockk<Race>())

        every { dao.getRacesFlow(year) } returns flowOf(entityList)
        every { mapper.mapEntityListToDomainList(entityList) } returns domainList

        val result = repository.getRaces(year).first()

        assertEquals(domainList, result)
        verify { dao.getRacesFlow(year) }
        verify { mapper.mapEntityListToDomainList(entityList) }
    }

    @Test
    fun `refreshRaces fetches from api, maps and inserts to dao`() = runTest {
        val year = 2024
        val raceDtoList = listOf(mockk<RaceDto>())
        val response = RacesResponseDto(races = raceDtoList)
        val entityList = listOf(mockk<RaceEntity>())

        coEvery { api.getRacesByYear(year) } returns response
        every { mapper.mapDtoListToEntityList(raceDtoList, year) } returns entityList
        coEvery { dao.insertAll(entityList) } returns Unit

        repository.refreshRaces(year)

        coVerify(exactly = 1) { api.getRacesByYear(year) }
        verify(exactly = 1) { mapper.mapDtoListToEntityList(raceDtoList, year) }
        coVerify(exactly = 1) { dao.insertAll(entityList) }
    }

    @Test(expected = RuntimeException::class)
    fun `refreshRaces throws exception when api fails`() = runTest {
        val year = 2024
        coEvery { api.getRacesByYear(year) } throws RuntimeException("Network Error")

        repository.refreshRaces(year)
        coVerify(exactly = 0) { dao.insertAll(any()) }
    }
}
