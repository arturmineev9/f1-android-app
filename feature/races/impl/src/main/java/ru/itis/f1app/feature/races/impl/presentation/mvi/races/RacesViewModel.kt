package ru.itis.f1app.feature.races.impl.presentation.mvi.races

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.viewmodel.container
import ru.itis.f1app.core.common.firebase.analytics.AnalyticsTracker
import ru.itis.f1app.core.common.mvi.BaseViewModel
import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions
import ru.itis.f1app.feature.races.api.domain.usecase.GetRacesUseCase
import ru.itis.f1app.feature.races.api.domain.usecase.RefreshRacesUseCase
import java.time.Year
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val getRacesUseCase: GetRacesUseCase,
    private val refreshRacesUseCase: RefreshRacesUseCase,
    private val analyticsTracker: AnalyticsTracker,
) : BaseViewModel<RacesState, RacesSideEffect>() {

    override val container = container<RacesState, RacesSideEffect>(RacesState())

    private var racesSubscription: Job? = null

    init {
        analyticsTracker.trackScreenView("Races_Screen")
        val currentYear = Year.now().value - 1
        intent {
            reduce { state.copy(selectedYear = currentYear) }
            loadRaces(currentYear)
        }
    }

    fun onRefreshClicked() = intent {
        refreshData(state.selectedYear)
    }

    fun onRaceClicked(raceId: String) = intent {
        val selectedRace = state.races.find { it.id == raceId }

        if (selectedRace != null) {
            postSideEffect(
                RacesSideEffect.NavigateToDetails(
                    raceId = selectedRace.id,
                    round = selectedRace.round,
                    year = state.selectedYear
                )
            )
        } else {
            postSideEffect(RacesSideEffect.ShowError(RacesExceptions.Unknown(Exception("Race not found"))))
        }
    }

    fun clearError() = intent {
        reduce { state.copy(error = null) }
    }

    private fun loadRaces(year: Int) = intent {
        racesSubscription?.cancel()

        racesSubscription = getRacesUseCase(year)
            .onEach { races ->
                reduce { state.copy(races = races) }
            }
            .launchIn(viewModelScope)

        refreshData(year)
    }

    private fun refreshData(year: Int) = intent {
        if (state.isLoading) return@intent

        reduce { state.copy(isLoading = true, error = null) }
        try {
            refreshRacesUseCase(year)
            reduce { state.copy(isLoading = false) }
        } catch (e: RacesExceptions) {
            reduce { state.copy(isLoading = false, error = e) }
            postSideEffect(RacesSideEffect.ShowError(e))
        } catch (e: Exception) {
            val unknown = RacesExceptions.Unknown(e)
            reduce { state.copy(isLoading = false, error = unknown) }
            postSideEffect(RacesSideEffect.ShowError(unknown))
        }
    }
}
