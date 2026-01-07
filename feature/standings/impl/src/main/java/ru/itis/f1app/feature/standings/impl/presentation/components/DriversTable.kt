package ru.itis.f1app.feature.standings.impl.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding

@Composable
fun DriversTable(
    drivers: List<DriverStanding>,
    onDriverClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("#", modifier = Modifier.width(30.dp), fontWeight = FontWeight.Bold)
                Text("Driver", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("Team", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("Pts", modifier = Modifier.width(50.dp), textAlign = TextAlign.End, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider()
        }

        items(drivers) { driver ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDriverClick(driver.driverId) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = driver.position.toString(),
                    modifier = Modifier.width(30.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${driver.driverName} ${driver.driverSurname}",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = driver.teamName,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = driver.points.toString(),
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        }
    }
}
