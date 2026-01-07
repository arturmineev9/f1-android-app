package ru.itis.f1app.feature.auth.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.f1app.feature.auth.api.repository.AuthRepository
import ru.itis.f1app.feature.auth.api.usecase.IsUserLoggedInUseCase
import ru.itis.f1app.feature.auth.api.usecase.LoginUseCase
import ru.itis.f1app.feature.auth.api.usecase.RegisterUseCase
import ru.itis.f1app.feature.auth.impl.data.repository.AuthRepositoryImpl
import ru.itis.f1app.feature.auth.impl.domain.usecase.IsUserLoggedInUseCaseImpl
import ru.itis.f1app.feature.auth.impl.domain.usecase.LoginUseCaseImpl
import ru.itis.f1app.feature.auth.impl.domain.usecase.RegisterUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthDomainModule {
    @Binds
    fun bindRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    fun bindIsUserLoggedInUseCase(impl: IsUserLoggedInUseCaseImpl): IsUserLoggedInUseCase

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
