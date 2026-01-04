package ru.itis.f1app.feature.auth.impl.presentation.viewmodel

import ru.itis.f1app.feature.auth.api.exception.AuthExceptions

data class AuthState(
    val isLoading: Boolean = false,
    val error: AuthExceptions? = null
)
