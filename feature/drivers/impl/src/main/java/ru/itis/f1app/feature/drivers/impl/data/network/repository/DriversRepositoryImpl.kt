package ru.itis.f1app.feature.drivers.impl.data.network.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails
import ru.itis.f1app.feature.drivers.api.domain.repository.DriversRepository
import ru.itis.f1app.feature.drivers.impl.data.mapper.DriverDetailsMapper
import ru.itis.f1app.feature.drivers.impl.data.network.datasource.DriversRemoteDataSource
import ru.itis.f1app.feature.drivers.impl.domain.exception.DriversExceptions
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DriversRepositoryImpl @Inject constructor(
    private val remoteDataSource: DriversRemoteDataSource,
    private val mapper: DriverDetailsMapper
) : DriversRepository {

    override fun getDriverDetails(year: String, driverId: String): Flow<DriverDetails> = flow {
        try {
            val response = remoteDataSource.getDriverDetails(year, driverId)

            val domain = with(mapper) { response.toDomain() }
            emit(domain)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }

    private fun mapToDomainException(e: Exception): DriversExceptions =
        when (e) {
            is IOException -> DriversExceptions.NetworkConnection(e)
            is HttpException -> DriversExceptions.ServerError(e.code(), e.message())
            is DriversExceptions -> e
            else -> DriversExceptions.Unknown(e)
        }
}
