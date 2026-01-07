package ru.itis.f1app.feature.drivers.impl.data.network.datasource

import ru.itis.f1app.feature.drivers.impl.data.network.dto.DriverDetailsResponseDto

interface DriversRemoteDataSource {
    suspend fun getDriverDetails(year: String, driverId: String): DriverDetailsResponseDto
}
