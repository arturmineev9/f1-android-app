package ru.itis.f1app.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.itis.f1app.feature.auth.api.usecase.IsUserLoggedInUseCase
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {
    suspend fun checkAuthStatus(): Boolean = isUserLoggedInUseCase()
}