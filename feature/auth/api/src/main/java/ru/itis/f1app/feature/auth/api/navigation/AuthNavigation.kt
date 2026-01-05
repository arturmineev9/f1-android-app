package ru.itis.f1app.feature.auth.api.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class AuthNavigation : ScreenProvider {
    object LoginScreen : AuthNavigation()
    object RegisterScreen : AuthNavigation()
}
