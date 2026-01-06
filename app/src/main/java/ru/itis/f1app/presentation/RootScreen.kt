package ru.itis.f1app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import ru.itis.f1app.core.navigation.SharedScreens

class RootScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<RootViewModel>()

        LaunchedEffect(Unit) {
            val isLoggedIn = viewModel.checkAuthStatus()
            if (isLoggedIn) {

                navigator.replace(ScreenRegistry.get(SharedScreens.Main))
            } else {
                navigator.replace(ScreenRegistry.get(SharedScreens.Login))
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Companion.Center) {
            CircularProgressIndicator()
        }
    }
}
