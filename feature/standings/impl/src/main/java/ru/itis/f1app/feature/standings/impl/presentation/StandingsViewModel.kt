package ru.itis.f1app.feature.standings.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.itis.f1app.feature.standings.api.domain.usecase.GetConstructorStandingsUseCase
import ru.itis.f1app.feature.standings.api.domain.usecase.GetDriverStandingsUseCase
import ru.itis.f1app.feature.standings.impl.presentation.mvi.StandingsSideEffect
import ru.itis.f1app.feature.standings.impl.presentation.mvi.StandingsState
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val getDriverStandings: GetDriverStandingsUseCase,
    private val getConstructorStandings: GetConstructorStandingsUseCase
) : ContainerHost<StandingsState, StandingsSideEffect>, ViewModel() {

    override val container = container<StandingsState, StandingsSideEffect>(StandingsState())

    init {
        loadData()
    }

    fun loadData() = intent {

        getDriverStandings("current")
            .onStart {
                reduce { state.copy(isLoading = true, error = null) }
            }
            .onEach { drivers ->
                reduce { state.copy(drivers = drivers, isLoading = false) }
            }
            .catch { exception ->
                reduce { state.copy(isLoading = false, error = exception.localizedMessage) }
                postSideEffect(StandingsSideEffect.ShowError(exception.message ?: "Unknown error"))
            }
            .launchIn(viewModelScope)

        getConstructorStandings("current")
            .onStart {
            }
            .onEach { teams ->
                reduce { state.copy(constructors = teams) }
            }
            .catch { exception ->
                postSideEffect(StandingsSideEffect.ShowError(exception.message ?: "Error loading teams"))
            }
            .launchIn(viewModelScope)
    }

    fun onTabSelected(index: Int) = intent {
        reduce { state.copy(selectedTab = index) }
    }

    fun onDriverClicked(driverId: String) = intent {
        postSideEffect(StandingsSideEffect.NavigateToDriverDetails(driverId))
    }
}
