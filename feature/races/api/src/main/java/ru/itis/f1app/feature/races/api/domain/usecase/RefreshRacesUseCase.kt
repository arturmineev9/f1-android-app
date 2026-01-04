package ru.itis.f1app.feature.races.api.domain.usecase

interface RefreshRacesUseCase {
    suspend operator fun invoke(year: Int)
}
