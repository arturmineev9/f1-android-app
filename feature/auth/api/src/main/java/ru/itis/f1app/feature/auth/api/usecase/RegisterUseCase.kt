package ru.itis.f1app.feature.auth.api.usecase

import ru.itis.f1app.core.common.utils.Result

interface RegisterUseCase {
    suspend operator fun invoke(username: String, password: String): Result<Unit>
}
