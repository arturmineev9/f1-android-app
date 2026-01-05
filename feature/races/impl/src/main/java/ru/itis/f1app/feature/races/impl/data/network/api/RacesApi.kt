package ru.itis.f1app.feature.races.impl.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.itis.f1app.feature.races.impl.data.network.dto.race.RacesResponse
import ru.itis.f1app.feature.races.impl.data.network.dto.race_details.RaceDetailsResponse

interface RacesApi {
    @GET("{year}")
    suspend fun getRacesByYear(@Path("year") year: Int): RacesResponse

    @GET("{year}/{round}/results.json")
    suspend fun getRaceResults(
        @Path("year") year: Int,
        @Path("round") round: Int
    ): RaceDetailsResponse

}
