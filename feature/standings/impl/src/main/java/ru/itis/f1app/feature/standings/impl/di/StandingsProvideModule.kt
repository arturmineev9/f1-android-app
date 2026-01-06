package ru.itis.f1app.feature.standings.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.itis.f1app.feature.standings.impl.data.network.api.StandingsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StandingsProvideModule {

    @Provides
    @Singleton
    fun provideStandingsApi(retrofit: Retrofit): StandingsApi {
        return retrofit.create(StandingsApi::class.java)
    }
}
