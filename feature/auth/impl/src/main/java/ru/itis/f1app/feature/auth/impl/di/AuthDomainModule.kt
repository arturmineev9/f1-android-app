package ru.itis.f1app.feature.auth.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.f1app.feature.auth.api.repositories.AuthRepository
import ru.itis.f1app.feature.auth.api.usecases.LoginUseCase
import ru.itis.f1app.feature.auth.api.usecases.RegisterUseCase
import ru.itis.f1app.feature.auth.impl.domain.repository.AuthRepositoryImpl
import ru.itis.f1app.feature.auth.impl.domain.usecases.LoginUseCaseImpl
import ru.itis.f1app.feature.auth.impl.domain.usecases.RegisterUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthDomainModule {
    @Binds
    fun bindRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
