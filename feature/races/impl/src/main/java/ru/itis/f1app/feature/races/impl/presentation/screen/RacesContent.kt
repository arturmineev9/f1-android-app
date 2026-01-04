package ru.itis.f1app.feature.races.impl.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.races.api.domain.model.Race
import ru.itis.f1app.feature.races.impl.R
import ru.itis.f1app.feature.races.impl.presentation.mvi.RacesState
import ru.itis.f1app.feature.races.impl.presentation.utils.formattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RacesContent(
    state: RacesState,
    snackBarHostState: SnackbarHostState,
    onRefreshClick: () -> Unit,
    onRaceClick: (String) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.f1_schedule_title, state.selectedYear)) },
                actions = {
                    IconButton(onClick = onRefreshClick) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.refresh))
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {

            when {
                state.error != null && state.races.isEmpty() -> {
                    RacesErrorScreen(error = state.error, onRetry = onRefreshClick)
                }

                state.isLoading && state.races.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.races.isNotEmpty() -> {
                    RacesList(races = state.races, onRaceClick = onRaceClick)
                }

                else -> {
                    Text(
                        text = stringResource(R.string.error_no_data),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (state.isLoading && state.races.isNotEmpty()) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter))
            }
        }
    }
}

@Composable
private fun RacesList(races: List<Race>, onRaceClick: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(races, key = { it.id }) { race ->
            RaceItem(race = race, onClick = { onRaceClick(race.id) })
        }
    }
}

@Composable
private fun RaceItem(race: Race, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Round ${race.round}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = race.formattedDate,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = race.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${race.circuitName}, ${race.city} (${race.country})",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
