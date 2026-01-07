package ru.itis.f1app.feature.races.impl.presentation.mvi.races.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.itis.f1app.feature.races.api.domain.usecase.GetRaceDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class RaceDetailsViewModel @Inject constructor(
    private val getRaceDetailsUseCase: GetRaceDetailsUseCase
) : ViewModel(), ContainerHost<RaceDetailsState, RaceDetailsSideEffect> {

    override val container = container<RaceDetailsState, RaceDetailsSideEffect>(RaceDetailsState())

    fun loadRaceDetails(year: Int, round: Int) = intent {
        if (state.details != null) return@intent

        reduce { state.copy(isLoading = true, error = null) }

        try {
            val result = getRaceDetailsUseCase(year, round)

            reduce {
                state.copy(
                    isLoading = false,
                    details = result
                )
            }
        } catch (e: Exception) {
            reduce {
                state.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown Error"
                )
            }
        }
    }

    fun onDriverClicked(driverId: String) = intent {
        postSideEffect(RaceDetailsSideEffect.NavigateToDriverDetails(driverId))
    }
    fun onBackClicked() = intent {
        postSideEffect(RaceDetailsSideEffect.NavigateBack)
    }
}
