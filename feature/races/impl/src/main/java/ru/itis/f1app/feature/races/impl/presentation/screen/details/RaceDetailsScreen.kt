package ru.itis.f1app.feature.races.impl.presentation.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
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
import ru.itis.f1app.feature.races.impl.presentation.screen.details.components.RaceDetailsContent

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

        LaunchedEffect(raceId) {
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

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = {
                        Text(
                            text = state.details?.raceName ?: "Race Details",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.onBackClicked() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface
                    )
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
                            text = state.error ?: "Unknown error",
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
