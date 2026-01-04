package ru.itis.f1app.feature.auth.api.usecase

interface IsUserLoggedInUseCase {
    suspend operator fun invoke(): Boolean
}
