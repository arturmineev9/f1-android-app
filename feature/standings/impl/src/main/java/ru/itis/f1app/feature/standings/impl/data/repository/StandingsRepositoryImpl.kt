package ru.itis.f1app.feature.standings.impl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding
import ru.itis.f1app.feature.standings.api.domain.repository.StandingsRepository
import ru.itis.f1app.feature.standings.impl.data.mapper.StandingsMapper
import ru.itis.f1app.feature.standings.impl.data.network.datasource.StandingsRemoteDataSource
import ru.itis.f1app.feature.standings.impl.domain.exception.StandingsExceptions
import java.io.IOException
import javax.inject.Inject

class StandingsRepositoryImpl @Inject constructor(
    private val remoteDataSource: StandingsRemoteDataSource,
    private val mapper: StandingsMapper
) : StandingsRepository {

    override fun getDriverStandings(year: String): Flow<List<DriverStanding>> = flow {
        try {
            val remoteData = remoteDataSource.getDriverStandings(year)

            if (remoteData.isEmpty()) {
                throw StandingsExceptions.NoDataAvailable()
            }

            val domainData = with(mapper) {
                remoteData.map { it.toDomain() }
            }

            emit(domainData)

        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }

    override fun getConstructorStandings(year: String): Flow<List<ConstructorStanding>> = flow {
        try {
            val remoteData = remoteDataSource.getConstructorStandings(year)

            if (remoteData.isEmpty()) {
                throw StandingsExceptions.NoDataAvailable()
            }

            val domainData = with(mapper) {
                remoteData.map { it.toDomain() }
            }

            emit(domainData)

        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }

    private fun mapToDomainException(e: Exception): StandingsExceptions {
        return when (e) {
            is IOException -> StandingsExceptions.NetworkConnection(e)
            is HttpException -> StandingsExceptions.ServerError(e.code(), e.message())
            is StandingsExceptions -> e
            else -> StandingsExceptions.Unknown(e)
        }
    }
}
