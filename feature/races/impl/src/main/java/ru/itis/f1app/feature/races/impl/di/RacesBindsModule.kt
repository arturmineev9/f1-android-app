package ru.itis.f1app.feature.races.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.f1app.feature.races.api.domain.repository.RacesRepository
import ru.itis.f1app.feature.races.api.domain.usecase.GetRacesUseCase
import ru.itis.f1app.feature.races.api.domain.usecase.RefreshRacesUseCase
import ru.itis.f1app.feature.races.impl.data.network.datasource.RacesRemoteDataSource
import ru.itis.f1app.feature.races.impl.data.network.datasource.RacesRemoteDataSourceImpl
import ru.itis.f1app.feature.races.impl.data.network.repository.RacesRepositoryImpl
import ru.itis.f1app.feature.races.impl.domain.usecase.GetRacesUseCaseImpl
import ru.itis.f1app.feature.races.impl.domain.usecase.RefreshRacesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RacesBindsModule {

    @Binds
    @Singleton
    abstract fun bindRacesRepository(impl: RacesRepositoryImpl): RacesRepository

    @Binds
    abstract fun bindGetRacesUseCase(impl: GetRacesUseCaseImpl): GetRacesUseCase

    @Binds
    abstract fun bindRefreshRacesUseCase(impl: RefreshRacesUseCaseImpl): RefreshRacesUseCase

    @Binds
    abstract fun bindRacesRemoteDataSource(impl: RacesRemoteDataSourceImpl): RacesRemoteDataSource
}
