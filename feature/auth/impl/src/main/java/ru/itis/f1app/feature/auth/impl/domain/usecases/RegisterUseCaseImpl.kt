package ru.itis.f1app.feature.auth.impl.domain.usecases

import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.repositories.AuthRepository
import ru.itis.f1app.feature.auth.api.usecases.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : RegisterUseCase {

    override suspend operator fun invoke(username: String, password: String): Result<Unit> {
        if (username.isBlank()) {
            return Result.Error(IllegalArgumentException("Username cannot be empty"))
        }
        if (password.length < 6) {
            return Result.Error(IllegalArgumentException("Password must be at least 6 characters"))
        }
        return repository.register(username, password)
    }
}
