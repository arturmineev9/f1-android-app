package ru.itis.f1app.feature.auth.impl.domain.usecases

import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.repositories.AuthRepository
import ru.itis.f1app.feature.auth.api.usecases.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : LoginUseCase {

    override suspend operator fun invoke(username: String, password: String): Result<Unit> {
        if (username.isBlank() || password.isBlank()) {
            return Result.Error(IllegalArgumentException("Fields cannot be empty"))
        }
        return repository.login(username, password)
    }
}
