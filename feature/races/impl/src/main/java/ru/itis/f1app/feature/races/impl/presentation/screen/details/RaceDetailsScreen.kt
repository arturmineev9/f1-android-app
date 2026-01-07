package ru.itis.f1app.feature.races.impl.presentation.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.itis.f1app.core.navigation.SharedScreens
import ru.itis.f1app.feature.races.impl.presentation.mvi.races.details.RaceDetailsSideEffect
import ru.itis.f1app.feature.races.impl.presentation.mvi.races.details.RaceDetailsViewModel

data class RaceDetailsScreen(
    val raceId: String,
    val year: Int,
    val round: Int
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent ?: LocalNavigator.currentOrThrow
        val viewModel = getViewModel<RaceDetailsViewModel>()
        val state by viewModel.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadRaceDetails(year, round)
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                RaceDetailsSideEffect.NavigateBack -> navigator.pop()
                is RaceDetailsSideEffect.NavigateToDriverDetails -> {
                    val screen = ScreenRegistry.get(
                        SharedScreens.DriverDetails(sideEffect.driverId)
                    )
                    navigator.push(screen)
                }
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(state.details?.raceName ?: "Race Details") },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.onBackClicked() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    state.error != null -> {
                        Text(
                            text = state.error ?: "Error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    state.details != null -> {
                        RaceDetailsContent(
                            details = state.details!!,
                            onDriverClick = { driverId -> viewModel.onDriverClicked(driverId) }
                        )
                    }
                }
            }
        }
    }
}
