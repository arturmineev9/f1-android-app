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
import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.impl.data.mapper.RaceMapper
import ru.itis.f1app.feature.races.impl.data.network.datasource.RacesRemoteDataSource
import ru.itis.f1app.feature.races.impl.data.network.dto.race.RaceDto as ListRaceDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race.RacesResponse
import ru.itis.f1app.feature.races.impl.data.network.dto.race_details.RaceDto as DetailsRaceDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race_details.MrDataDto
import ru.itis.f1app.feature.races.impl.data.network.dto.race_details.RaceDetailsResponse
import ru.itis.f1app.feature.races.impl.data.network.dto.race_details.RaceTableDto
import ru.itis.f1app.feature.races.impl.data.network.repository.RacesRepositoryImpl

class RacesRepositoryImplTest {

    private val remoteDataSource: RacesRemoteDataSource = mockk()
    private val dao: RacesDao = mockk()
    private val mapper: RaceMapper = mockk()

    private val repository = RacesRepositoryImpl(remoteDataSource, dao, mapper)

    @Test
    fun `getRaces returns mapped domain models from dao`() = runTest {
        val year = 2025
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
    fun `refreshRaces fetches from dataSource, maps and inserts to dao`() = runTest {
        val year = 2025

        val raceDtoList = listOf(mockk<ListRaceDto>())
        val response = RacesResponse(races = raceDtoList)
        val entityList = listOf(mockk<RaceEntity>())

        coEvery { remoteDataSource.getRaces(year) } returns response
        every { mapper.mapDtoListToEntityList(raceDtoList, year) } returns entityList
        coEvery { dao.insertAll(entityList) } returns Unit

        repository.refreshRaces(year)

        coVerify(exactly = 1) { remoteDataSource.getRaces(year) }
        verify(exactly = 1) { mapper.mapDtoListToEntityList(raceDtoList, year) }
        coVerify(exactly = 1) { dao.insertAll(entityList) }
    }

    @Test(expected = RacesExceptions.Unknown::class)
    fun `refreshRaces throws exception when dataSource fails`() = runTest {
        val year = 2025
        coEvery { remoteDataSource.getRaces(year) } throws RuntimeException("Network Error")

        repository.refreshRaces(year)

        coVerify(exactly = 0) { dao.insertAll(any()) }
    }

    @Test
    fun `getRaceDetails returns mapped domain model when successful`() = runTest {
        val year = 2024
        val round = 1

        val raceDto = mockk<DetailsRaceDto>(relaxed = true) {
        }

        val mrData = MrDataDto(RaceTableDto(listOf(raceDto)))
        val response = RaceDetailsResponse(mrData)

        coEvery { remoteDataSource.getRaceDetails(year, round) } returns response

        val result = repository.getRaceDetails(year, round)

        coVerify(exactly = 1) { remoteDataSource.getRaceDetails(year, round) }
    }

    @Test(expected = RacesExceptions.NoDataAvailable::class)
    fun `getRaceDetails throws NoDataAvailable when list is empty`() = runTest {
        val year = 2024
        val round = 1

        val mrData = MrDataDto(RaceTableDto(emptyList()))
        val response = RaceDetailsResponse(mrData)

        coEvery { remoteDataSource.getRaceDetails(year, round) } returns response

        repository.getRaceDetails(year, round)
    }
}
