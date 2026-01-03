package ru.itis.f1app.feature.auth.impl.domain.usecase

import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.exception.AuthException
import ru.itis.f1app.feature.auth.api.repository.AuthRepository
import ru.itis.f1app.feature.auth.api.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : RegisterUseCase {

    override suspend operator fun invoke(username: String, password: String): Result<Unit> {
        if (username.isBlank()) {
            return Result.Error(AuthException.EmptyUsername())
        }
        if (password.length < 6) {
            return Result.Error(AuthException.ShortPassword())
        }
        return repository.register(username, password)
    }
}
