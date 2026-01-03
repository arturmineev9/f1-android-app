package ru.itis.f1app.feature.auth.api.repositories

import ru.itis.f1app.core.common.utils.Result

interface AuthRepository {
    suspend fun register(username: String, password: String): Result<Unit>
    suspend fun login(username: String, password: String): Result<Unit>
    suspend fun logout()
}
