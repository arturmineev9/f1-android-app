package ru.itis.f1app.feature.auth.impl.domain.usecase

import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.exception.AuthException
import ru.itis.f1app.feature.auth.api.repository.AuthRepository
import ru.itis.f1app.feature.auth.api.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : LoginUseCase {

    override suspend operator fun invoke(username: String, password: String): Result<Unit> {
        if (username.isBlank() || password.isBlank()) {
            return Result.Error(AuthException.EmptyFields())
        }
        return repository.login(username, password)
    }
}
