package ru.itis.f1app.feature.auth.impl.data.repository

import ru.itis.f1app.core.common.token.TokenStorage
import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.core.common.utils.SecurityUtils
import ru.itis.f1app.core.database.dao.UserDao
import ru.itis.f1app.core.database.entity.UserEntity
import ru.itis.f1app.feature.auth.api.exception.AuthExceptions
import ru.itis.f1app.feature.auth.api.repository.AuthRepository
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun register(username: String, password: String): Result<Unit> {
        return try {
            val existing = userDao.getUserByUsername(username)
            if (existing != null) {
                return Result.Error(AuthExceptions.UserAlreadyExists())
            }

            val passwordHash = SecurityUtils.hashPassword(password)
            userDao.insertUser(UserEntity(username = username, passwordHash = passwordHash))

            val fakeToken = generateFakeJwt()
            tokenStorage.saveToken(fakeToken)

            Result.Success(Unit)
        } catch (e: IOException) {
            Result.Error(e)
        } catch (e: SecurityException) {
            Result.Error(e)
        } catch (e: IllegalStateException) {
            Result.Error(e)
        }
    }

    override suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            val user = userDao.getUserByUsername(username)
            val inputHash = SecurityUtils.hashPassword(password)

            if (user != null && user.passwordHash == inputHash) {
                val fakeToken = generateFakeJwt()
                tokenStorage.saveToken(fakeToken)
                Result.Success(Unit)
            } else {
                return Result.Error(AuthExceptions.InvalidCredentials())
            }
        } catch (e: IOException) {
            Result.Error(e)
        } catch (e: SecurityException) {
            Result.Error(e)
        } catch (e: IllegalStateException) {
            Result.Error(e)
        }
    }

    private fun generateFakeJwt(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.${UUID.randomUUID()}.fakeSignature"
    }

    override suspend fun logout() {
        tokenStorage.clearToken()
    }
}
