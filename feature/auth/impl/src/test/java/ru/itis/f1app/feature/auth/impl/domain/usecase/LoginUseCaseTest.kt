package ru.itis.f1app.feature.auth.impl.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.exception.AuthExceptions
import ru.itis.f1app.feature.auth.api.repository.AuthRepository

class LoginUseCaseTest {

    private val repository: AuthRepository = mockk()
    private val useCase = LoginUseCaseImpl(repository)

    @Test
    fun `invoke should return EmptyFields error when username is blank`() = runTest {
        val result = useCase("", "pass")
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).exception is AuthExceptions.EmptyFields)
    }

    @Test
    fun `invoke should return EmptyFields error when password is blank`() = runTest {
        val result = useCase("user", "")
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).exception is AuthExceptions.EmptyFields)
    }

    @Test
    fun `invoke should call repository when fields are valid`() = runTest {
        coEvery { repository.login(any(), any()) } returns Result.Success(Unit)

        val result = useCase("user", "pass")

        assertTrue(result is Result.Success)
        coVerify { repository.login("user", "pass") }
    }
}
