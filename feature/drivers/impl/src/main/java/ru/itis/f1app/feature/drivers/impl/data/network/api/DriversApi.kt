package ru.itis.f1app.feature.drivers.impl.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.itis.f1app.feature.drivers.impl.data.network.dto.DriverDetailsResponseDto

interface DriversApi {

    @GET("{year}/drivers/{driverId}")
    suspend fun getDriverDetails(
        @Path("year") year: String,
        @Path("driverId") driverId: String
    ): DriverDetailsResponseDto
}
