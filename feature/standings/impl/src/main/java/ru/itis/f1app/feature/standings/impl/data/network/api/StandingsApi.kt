package ru.itis.f1app.feature.standings.impl.data.network.api

import ru.itis.f1app.feature.standings.impl.data.network.dto.ConstructorStandingsResponseDto
import ru.itis.f1app.feature.standings.impl.data.network.dto.DriverStandingsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface StandingsApi {

    @GET("api/{year}/drivers-championship")
    suspend fun getDriverStandings(
        @Path("year") year: String
    ): DriverStandingsResponseDto

    @GET("api/{year}/constructors-championship")
    suspend fun getConstructorStandings(
        @Path("year") year: String
    ): ConstructorStandingsResponseDto
}
