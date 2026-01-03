package ru.itis.f1app.feature.auth.api.exception

sealed class AuthException(override val message: String? = null) : Exception(message) {
    data class EmptyUsername(val msg: String? = null) : AuthException(msg)
    data class EmptyFields(val msg: String? = null) : AuthException(msg)
    data class ShortPassword(val msg: String? = null) : AuthException(msg)
    data class UserAlreadyExists(val msg: String? = null) : AuthException(msg)
    data class InvalidCredentials(val msg: String? = null) : AuthException(msg)
}
