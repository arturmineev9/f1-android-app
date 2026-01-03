package ru.itis.f1app.feature.auth.impl.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.exception.AuthException
import ru.itis.f1app.feature.auth.api.repository.AuthRepository

class RegisterUseCaseTest {

    private val repository: AuthRepository = mockk()
    private val useCase = RegisterUseCaseImpl(repository)

    @Test
    fun `invoke should return EmptyUsername error when username is blank`() = runTest {
        val result = useCase("  ", "password123")
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).exception is AuthException.EmptyUsername)
        coVerify(exactly = 0) { repository.register(any(), any()) }
    }

    @Test
    fun `invoke should return ShortPassword error when password is short`() = runTest {
        val result = useCase("user", "12345") // 5 chars
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).exception is AuthException.ShortPassword)
        coVerify(exactly = 0) { repository.register(any(), any()) }
    }

    @Test
    fun `invoke should return Success when data is valid`() = runTest {
        coEvery { repository.register(any(), any()) } returns Result.Success(Unit)

        val result = useCase("validUser", "password123")

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { repository.register("validUser", "password123") }
    }
}
