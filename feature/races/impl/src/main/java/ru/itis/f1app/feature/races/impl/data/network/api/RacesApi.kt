package ru.itis.f1app.feature.races.impl.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.itis.f1app.feature.races.impl.data.network.dto.RacesResponseDto

interface RacesApi {
    @GET("{year}")
    suspend fun getRacesByYear(@Path("year") year: Int): RacesResponseDto
}
