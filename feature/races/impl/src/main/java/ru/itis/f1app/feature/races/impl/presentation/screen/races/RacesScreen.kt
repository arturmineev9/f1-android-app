package ru.itis.f1app.feature.races.impl.presentation.screen.races

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions
import ru.itis.f1app.feature.races.impl.R
import ru.itis.f1app.feature.races.impl.presentation.mvi.races.RacesSideEffect
import ru.itis.f1app.feature.races.impl.presentation.mvi.races.RacesViewModel
import ru.itis.f1app.feature.races.impl.presentation.screen.details.RaceDetailsScreen

class RacesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<RacesViewModel>()
        val state by viewModel.collectAsState()
        val context = LocalContext.current
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        viewModel.collectSideEffect { effect ->
            when (effect) {
                is RacesSideEffect.ShowError -> {
                    if (state.races.isNotEmpty()) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = getErrorMessage(context, effect.exception)
                            )
                        }
                    }
                }
                is RacesSideEffect.NavigateToDetails -> {
                    navigator.push(
                        RaceDetailsScreen(
                            raceId = effect.raceId,
                            year = effect.year,
                            round = effect.round
                        )
                    )
                }
            }
        }

        RacesContent(
            state = state,
            snackBarHostState = snackBarHostState,
            onRefreshClick = viewModel::onRefreshClicked,
            onRaceClick = { id -> viewModel.onRaceClicked(id) }
        )
    }

    private fun getErrorMessage(context: Context, error: RacesExceptions): String {
        return when (error) {
            is RacesExceptions.NetworkConnection -> context.getString(R.string.error_network_connection)
            is RacesExceptions.NoDataAvailable -> context.getString(R.string.error_no_data)
            is RacesExceptions.ServerError -> context.getString(R.string.error_server, error.code)
            else -> context.getString(R.string.error_unknown)
        }
    }
}
