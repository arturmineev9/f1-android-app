package ru.itis.f1app.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreens : ScreenProvider {
    data object Login : SharedScreens()
    data object Register : SharedScreens()

    data object Races : SharedScreens()
    data object Standings : SharedScreens()

    data object Main : SharedScreens()
}
