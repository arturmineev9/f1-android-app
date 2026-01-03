package ru.itis.f1app.feature.auth.impl.domain.usecase

import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.exception.AuthExceptions
import ru.itis.f1app.feature.auth.api.repository.AuthRepository
import ru.itis.f1app.feature.auth.api.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : RegisterUseCase {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    override suspend operator fun invoke(username: String, password: String): Result<Unit> {
        return when {
            username.isBlank() -> Result.Error(AuthExceptions.EmptyUsername())
            password.length < MIN_PASSWORD_LENGTH -> Result.Error(AuthExceptions.ShortPassword())
            else -> repository.register(username, password)
        }
    }
}
