package ru.itis.f1app.feature.auth.impl.domain.repository

import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.itis.f1app.core.common.token.TokenStorage
import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.core.common.utils.SecurityUtils
import ru.itis.f1app.core.database.dao.UserDao
import ru.itis.f1app.core.database.entity.UserEntity
import ru.itis.f1app.feature.auth.api.exception.AuthException

class AuthRepositoryImplTest {

    private val userDao: UserDao = mockk()
    private val tokenStorage: TokenStorage = mockk(relaxed = true)
    private val repository = AuthRepositoryImpl(userDao, tokenStorage)

    @Test
    fun `register should return UserAlreadyExists if user is found`() = runTest {
        coEvery { userDao.getUserByUsername("vasya") } returns UserEntity(username = "vasya", passwordHash = "hash")

        val result = repository.register("vasya", "123456")

        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).exception is AuthException.UserAlreadyExists)
    }

    @Test
    fun `register should hash password and save token on success`() = runTest {
        coEvery { userDao.getUserByUsername("vasya") } returns null
        coEvery { userDao.insertUser(any()) } returns 1L

        val expectedHash = SecurityUtils.hashPassword("password")

        val result = repository.register("vasya", "password")

        assertTrue(result is Result.Success)

        val slot = slot<UserEntity>()
        coVerify { userDao.insertUser(capture(slot)) }
        assertTrue(slot.captured.passwordHash == expectedHash)

        coVerify { tokenStorage.saveToken(any()) }
    }

    @Test
    fun `login should return InvalidCredentials if password hash does not match`() = runTest {
        val trueHash = SecurityUtils.hashPassword("correct")
        val user = UserEntity(username = "vasya", passwordHash = trueHash)
        coEvery { userDao.getUserByUsername("vasya") } returns user

        val result = repository.login("vasya", "wrong")

        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).exception is AuthException.InvalidCredentials)
    }

    @Test
    fun `login should save token on success`() = runTest {
        val trueHash = SecurityUtils.hashPassword("correct")
        val user = UserEntity(username = "vasya", passwordHash = trueHash)
        coEvery { userDao.getUserByUsername("vasya") } returns user

        val result = repository.login("vasya", "correct")

        assertTrue(result is Result.Success)
        coVerify { tokenStorage.saveToken(any()) }
    }
}
