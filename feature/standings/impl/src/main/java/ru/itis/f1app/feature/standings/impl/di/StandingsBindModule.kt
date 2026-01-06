package ru.itis.f1app.feature.standings.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.f1app.feature.standings.api.domain.repository.StandingsRepository
import ru.itis.f1app.feature.standings.api.domain.usecase.GetConstructorStandingsUseCase
import ru.itis.f1app.feature.standings.api.domain.usecase.GetDriverStandingsUseCase
import ru.itis.f1app.feature.standings.impl.data.network.datasource.StandingsRemoteDataSource
import ru.itis.f1app.feature.standings.impl.data.network.datasource.StandingsRemoteDataSourceImpl
import ru.itis.f1app.feature.standings.impl.data.repository.StandingsRepositoryImpl
import ru.itis.f1app.feature.standings.impl.domain.usecase.GetConstructorStandingsUseCaseImpl
import ru.itis.f1app.feature.standings.impl.domain.usecase.GetDriverStandingsUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StandingsBindModule {

    @Binds
    @Singleton
    abstract fun bindStandingsRepository(
        impl: StandingsRepositoryImpl
    ): StandingsRepository

    @Binds
    @Singleton
    abstract fun bindStandingsRemoteDataSource(
        impl: StandingsRemoteDataSourceImpl
    ): StandingsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindDriverStandingsUseCase(
        impl: GetDriverStandingsUseCaseImpl
    ): GetDriverStandingsUseCase

    @Binds
    @Singleton
    abstract fun bindConstructorStandingsUseCase(
        impl: GetConstructorStandingsUseCaseImpl
    ): GetConstructorStandingsUseCase
}
