package ru.itis.f1app.feature.auth.impl.domain.repository

import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.core.database.dao.UserDao
import ru.itis.f1app.core.database.entity.UserEntity
import ru.itis.f1app.feature.auth.api.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : AuthRepository {
    
    private var currentUser: String? = null

    override suspend fun register(username: String, password: String): Result<Unit> {
        return try {
            val existing = userDao.getUserByUsername(username)
            if (existing != null) {
                return Result.Error(Exception("User already exists"))
            }
            userDao.insertUser(UserEntity(username = username, passwordHash = password))
            currentUser = username
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            val user = userDao.getUserByUsername(username)
            if (user != null && user.passwordHash == password) {
                currentUser = username
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Invalid credentials"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun logout() {
        currentUser = null
    }

    override suspend fun getCurrentUser(): String? = currentUser
}
