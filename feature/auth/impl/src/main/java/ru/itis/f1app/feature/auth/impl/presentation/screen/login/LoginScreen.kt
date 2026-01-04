package ru.itis.f1app.feature.auth.impl.presentation.screen.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.itis.f1app.core.navigation.SharedScreens
import ru.itis.f1app.feature.auth.impl.presentation.viewmodel.AuthSideEffect
import ru.itis.f1app.feature.auth.impl.presentation.viewmodel.AuthViewModel

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<AuthViewModel>()
        val state by viewModel.collectAsState()
        val context = LocalContext.current

        viewModel.collectSideEffect { effect ->
            when (effect) {
                AuthSideEffect.NavigateToMain -> {
                    val racesScreen = ScreenRegistry.get(SharedScreens.Races)
                    navigator.replaceAll(racesScreen)
                }

                is AuthSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        LoginContent(
            state = state,
            onLoginClick = viewModel::onLoginClicked,
            onRegisterClick = {
                val registerScreen = ScreenRegistry.get(SharedScreens.Register)
                navigator.push(registerScreen)
            },
            onValueChange = viewModel::clearError
        )
    }
}
