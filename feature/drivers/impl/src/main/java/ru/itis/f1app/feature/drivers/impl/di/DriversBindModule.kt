package ru.itis.f1app.feature.drivers.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.f1app.feature.drivers.api.domain.repository.DriversRepository
import ru.itis.f1app.feature.drivers.api.domain.usecase.GetDriverDetailsUseCase
import ru.itis.f1app.feature.drivers.impl.data.network.datasource.DriversRemoteDataSource
import ru.itis.f1app.feature.drivers.impl.data.network.datasource.DriversRemoteDataSourceImpl
import ru.itis.f1app.feature.drivers.impl.data.network.repository.DriversRepositoryImpl
import ru.itis.f1app.feature.drivers.impl.domain.usecase.GetDriverDetailsUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DriversBindModule {

    @Binds
    @Singleton
    abstract fun bindDriversRemoteDataSource(
        impl: DriversRemoteDataSourceImpl
    ): DriversRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindDriversRepository(
        impl: DriversRepositoryImpl
    ): DriversRepository

    @Binds
    @Singleton
    abstract fun bindGetDriverDetailsUseCase(
        impl: GetDriverDetailsUseCaseImpl
    ): GetDriverDetailsUseCase
}
