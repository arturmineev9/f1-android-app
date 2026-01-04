package ru.itis.f1app.feature.auth.impl.presentation.viewmodel

sealed interface AuthSideEffect {
    data object NavigateToMain : AuthSideEffect
    data class ShowToast(val message: String) : AuthSideEffect
}
