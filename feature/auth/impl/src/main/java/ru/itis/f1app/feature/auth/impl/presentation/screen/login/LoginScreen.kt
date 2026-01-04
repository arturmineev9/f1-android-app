package ru.itis.f1app.feature.auth.impl.presentation.screen.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.itis.f1app.feature.auth.impl.presentation.screen.register.RegisterScreen
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
                    Toast.makeText(context, "Welcome to F1 App!", Toast.LENGTH_SHORT).show()
                }
                is AuthSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        LoginContent(
            state = state,
            onLoginClick = viewModel::onLoginClicked,
            onRegisterClick = { navigator.push(RegisterScreen()) },
            onValueChange = viewModel::clearError
        )
    }
}
