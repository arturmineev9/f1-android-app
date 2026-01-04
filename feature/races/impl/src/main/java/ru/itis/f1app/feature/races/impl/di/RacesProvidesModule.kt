package ru.itis.f1app.feature.races.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.itis.f1app.feature.races.impl.data.network.api.RacesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RacesProvidesModule {

    @Provides
    @Singleton
    fun provideRacesApi(retrofit: Retrofit): RacesApi {
        return retrofit.create(RacesApi::class.java)
    }
}
