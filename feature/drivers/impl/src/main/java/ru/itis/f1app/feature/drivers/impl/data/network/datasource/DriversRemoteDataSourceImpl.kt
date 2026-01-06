package ru.itis.f1app.feature.drivers.impl.data.network.datasource

import ru.itis.f1app.feature.drivers.impl.data.network.api.DriversApi
import ru.itis.f1app.feature.drivers.impl.data.network.dto.DriverDetailsResponseDto
import javax.inject.Inject

class DriversRemoteDataSourceImpl @Inject constructor(
    private val api: DriversApi
) : DriversRemoteDataSource {

    override suspend fun getDriverDetails(year: String, driverId: String): DriverDetailsResponseDto {
        return api.getDriverDetails(year, driverId)
    }
}
