package ru.itis.f1app.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreens : ScreenProvider {
    object Races : SharedScreens()
    object Login : SharedScreens()
    object Register : SharedScreens()
}
