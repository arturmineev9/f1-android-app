package ru.itis.f1app.feature.drivers.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.itis.f1app.feature.drivers.impl.data.network.api.DriversApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DriversProvideModule {

    @Provides
    @Singleton
    fun provideDriversApi(retrofit: Retrofit): DriversApi =
        retrofit.create(DriversApi::class.java)
}
