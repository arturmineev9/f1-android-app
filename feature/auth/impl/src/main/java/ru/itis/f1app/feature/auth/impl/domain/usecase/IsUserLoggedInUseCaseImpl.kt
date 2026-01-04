package ru.itis.f1app.feature.auth.impl.domain.usecase

import ru.itis.f1app.core.common.token.TokenStorage
import ru.itis.f1app.feature.auth.api.usecase.IsUserLoggedInUseCase
import javax.inject.Inject

class IsUserLoggedInUseCaseImpl @Inject constructor(
    private val tokenStorage: TokenStorage
) : IsUserLoggedInUseCase {
    override suspend operator fun invoke(): Boolean {
        return !tokenStorage.getAccessToken().isNullOrBlank()
    }
}
