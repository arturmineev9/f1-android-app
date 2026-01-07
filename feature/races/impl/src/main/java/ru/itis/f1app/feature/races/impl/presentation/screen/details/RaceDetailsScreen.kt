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
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
        val viewModel = getViewModel<RaceDetailsViewModel>()
        val state by viewModel.collectAsState()
        val navigator = LocalNavigator.currentOrThrow.parent ?: LocalNavigator.currentOrThrow
        val snackBarHostState = remember { SnackbarHostState() }
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        LaunchedEffect(raceId) {
            viewModel.loadRaceDetails(year, round)
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                RaceDetailsSideEffect.NavigateBack -> navigator.pop()
                is RaceDetailsSideEffect.NavigateToDriverDetails -> {
                    navigator.push(ScreenRegistry.get(SharedScreens.DriverDetails(sideEffect.driverId)))
                }
            }
        }

        RaceDetailsScaffold(
            state = state,
            snackBarHostState = snackBarHostState,
            scrollBehavior = scrollBehavior,
            onBackClick = viewModel::onBackClicked,
            onDriverClick = viewModel::onDriverClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RaceDetailsScaffold(
    state: ru.itis.f1app.feature.races.impl.presentation.mvi.races.details.RaceDetailsState,
    snackBarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit,
    onDriverClick: (String) -> Unit
) {
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
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                state.error != null -> Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                state.details != null -> RaceDetailsContent(
                    details = state.details!!,
                    onDriverClick = onDriverClick
                )
            }
        }
    }
}
