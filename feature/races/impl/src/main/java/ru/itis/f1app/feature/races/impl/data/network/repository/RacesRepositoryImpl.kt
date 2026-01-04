package ru.itis.f1app.feature.races.impl.data.network.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import ru.itis.f1app.core.database.dao.RacesDao
import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.api.domain.repository.RacesRepository
import ru.itis.f1app.feature.races.impl.data.mapper.RaceMapper
import ru.itis.f1app.feature.races.impl.data.network.api.RacesApi
import java.io.IOException
import javax.inject.Inject

class RacesRepositoryImpl @Inject constructor(
    private val api: RacesApi,
    private val dao: RacesDao,
    private val mapper: RaceMapper
) : RacesRepository {

    override fun getRaces(year: Int): Flow<List<Race>> {
        return dao.getRacesFlow(year).map { entities ->
            mapper.mapEntityListToDomainList(entities)
        }
    }

    override suspend fun refreshRaces(year: Int) {
        try {
            val response = api.getRacesByYear(year)
            if (response.races.isEmpty()) {
                throw RacesExceptions.NoDataAvailable()
            }
            val entities = mapper.mapDtoListToEntityList(response.races, year)
            dao.insertAll(entities)
        } catch (e: Exception) {
            throw when (e) {
                is IOException -> RacesExceptions.NetworkConnection(e)
                is HttpException -> RacesExceptions.ServerError(e.code(), e.message())
                is RacesExceptions -> e
                else -> RacesExceptions.Unknown(e)
            }
        }
    }
}
