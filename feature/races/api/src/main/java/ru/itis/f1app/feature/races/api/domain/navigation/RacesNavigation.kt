package ru.itis.f1app.feature.races.api.domain.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class RacesNavigation : ScreenProvider {
    object RacesScreen : RacesNavigation()
}
