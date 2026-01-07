package ru.itis.f1app.feature.races.impl.presentation.screen.races.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.impl.R
import ru.itis.f1app.feature.races.impl.presentation.mvi.races.RacesState

private const val ANIMATION_DURATION = 300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RacesContent(
    state: RacesState,
    snackBarHostState: SnackbarHostState,
    onRefreshClick: () -> Unit,
    onRaceClick: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.f1_schedule_title, state.selectedYear),
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    if (!state.isLoading) {
                        IconButton(onClick = onRefreshClick) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = stringResource(R.string.refresh)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            RacesStateContent(state, onRefreshClick, onRaceClick)

            if (state.isLoading && state.races.isNotEmpty()) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun RacesStateContent(
    state: RacesState,
    onRefreshClick: () -> Unit,
    onRaceClick: (String) -> Unit
) {
    AnimatedContent(
        targetState = state,
        transitionSpec = {
            fadeIn(animationSpec = tween(ANIMATION_DURATION)) togetherWith fadeOut(
                animationSpec = tween(
                    ANIMATION_DURATION
                )
            )
        },
        label = "RacesContentTransition"
    ) { targetState ->
        when {
            targetState.error != null && targetState.races.isEmpty() -> {
                RacesErrorScreen(error = targetState.error, onRetry = onRefreshClick)
            }

            targetState.isLoading && targetState.races.isEmpty() -> {
                RacesLoadingSkeleton()
            }

            targetState.races.isNotEmpty() -> {
                RacesList(races = targetState.races, onRaceClick = onRaceClick)
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.error_no_data),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun RacesList(
    races: List<Race>,
    onRaceClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = races,
            key = { it.id }
        ) { race ->
            RaceCard(
                race = race,
                onClick = { onRaceClick(race.id) }
            )
        }
        item {
            Box(modifier = Modifier.padding(16.dp))
        }
    }
}
