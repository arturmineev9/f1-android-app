package ru.itis.f1app.feature.drivers.impl.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails
import ru.itis.f1app.feature.drivers.api.domain.model.DriverRaceResult

@Composable
fun DriverDetailsContent(
    modifier: Modifier = Modifier,
    state: DriverDetails
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            DriverHeader(state)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Recent races",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
        }

        items(state.recentResults) { result ->
            RaceResultItem(result)
            Divider()
        }
    }
}

@Composable
private fun DriverHeader(details: DriverDetails) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = details.fullName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        if (details.number != null) {
            Text(
                text = "Number: ${details.number}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        details.teamName?.let {
            Text(
                text = "Team: $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "Nationality: ${details.nationality}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Birth date: ${details.birthDate}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun RaceResultItem(result: DriverRaceResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = result.raceName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                text = "Round ${result.raceRound} • ${result.date}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Position: ${result.position}  •  Points: ${result.points}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
