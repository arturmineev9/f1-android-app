package ru.itis.f1app.feature.drivers.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.itis.f1app.core.common.firebase.analytics.AnalyticsTracker
import ru.itis.f1app.core.common.mvi.BaseViewModel
import ru.itis.f1app.feature.drivers.api.domain.usecase.GetDriverDetailsUseCase
import ru.itis.f1app.feature.drivers.impl.presentation.mvi.DriverDetailsSideEffect
import ru.itis.f1app.feature.drivers.impl.presentation.mvi.DriverDetailsState
import javax.inject.Inject

@HiltViewModel
class DriverDetailsViewModel @Inject constructor(
    private val getDriverDetailsUseCase: GetDriverDetailsUseCase,
    private val analyticsTracker: AnalyticsTracker
) : BaseViewModel<DriverDetailsState, DriverDetailsSideEffect>() {

    override val container = container<DriverDetailsState, DriverDetailsSideEffect>(DriverDetailsState())

    init {
        analyticsTracker.trackScreenView("Driver_Details_Screen")
    }

    fun loadDriverDetails(
        driverId: String,
        year: String = "current"
    ) = intent {
        getDriverDetailsUseCase(year, driverId)
            .onStart {
                reduce { state.copy(isLoading = true, error = null) }
            }
            .onEach { details ->
                reduce { state.copy(isLoading = false, driverDetails = details, error = null) }
            }
            .catch { throwable ->
                val message = throwable.message ?: "Unknown error"
                reduce { state.copy(isLoading = false, error = message) }
                postSideEffect(DriverDetailsSideEffect.ShowError(message))
            }
            .launchIn(viewModelScope)
    }

    fun onBackClicked() = intent {
        postSideEffect(DriverDetailsSideEffect.NavigateBack)
    }
}
