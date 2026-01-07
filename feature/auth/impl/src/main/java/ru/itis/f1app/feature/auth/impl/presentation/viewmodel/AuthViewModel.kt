package ru.itis.f1app.feature.auth.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.itis.f1app.core.common.firebase.analytics.AnalyticsTracker
import ru.itis.f1app.core.common.mvi.BaseViewModel
import ru.itis.f1app.core.common.utils.Result
import ru.itis.f1app.feature.auth.api.exception.AuthExceptions
import ru.itis.f1app.feature.auth.api.usecase.LoginUseCase
import ru.itis.f1app.feature.auth.api.usecase.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val analyticsTracker: AnalyticsTracker
) : BaseViewModel<AuthState, AuthSideEffect>() {

    override val container = container<AuthState, AuthSideEffect>(AuthState())

    init {
        analyticsTracker.trackScreenView("Auth_Screen")
    }

    fun onLoginClicked(user: String, pass: String) = intent {
        reduce { state.copy(isLoading = true, error = null) }

        when (val result = loginUseCase(user, pass)) {
            is Result.Success -> {
                reduce { state.copy(isLoading = false) }
                postSideEffect(AuthSideEffect.NavigateToMain)
            }
            is Result.Error -> {
                val authEx = result.exception as? AuthExceptions
                reduce { state.copy(isLoading = false, error = authEx) }

                if (authEx == null) {
                    postSideEffect(AuthSideEffect.ShowToast(result.exception.localizedMessage ?: "Error"))
                }
            }
            Result.Loading -> {}
        }
    }

    fun onRegisterClicked(user: String, pass: String) = intent {
        reduce { state.copy(isLoading = true, error = null) }

        when (val result = registerUseCase(user, pass)) {
            is Result.Success -> {
                reduce { state.copy(isLoading = false) }
                postSideEffect(AuthSideEffect.NavigateToMain)
            }
            is Result.Error -> {
                val authEx = result.exception as? AuthExceptions
                reduce { state.copy(isLoading = false, error = authEx) }
                if (authEx == null) {
                    postSideEffect(AuthSideEffect.ShowToast(result.exception.localizedMessage ?: "Error"))
                }
            }
            Result.Loading -> {}
        }
    }

    fun clearError() = intent {
        reduce { state.copy(error = null) }
    }
}
