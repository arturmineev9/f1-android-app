package ru.itis.f1app.feature.races.impl.presentation.screen.details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.races.api.domain.model.RaceDetails
import ru.itis.f1app.feature.races.impl.R

@Composable
fun RaceInfo(details: RaceDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.circuit_info),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRowWithIcon(
                    icon = painterResource(id = ru.itis.f1app.core.ui.R.drawable.ic_loop),
                    label = stringResource(R.string.circuit_name),
                    value = details.circuitName
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.surface
                )
                InfoRowWithIcon(
                    icon = painterResource(id = ru.itis.f1app.core.ui.R.drawable.ic_location),
                    label = stringResource(R.string.location),
                    value = details.location
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.surface
                )
                InfoRowWithIcon(
                    icon = painterResource(id = ru.itis.f1app.core.ui.R.drawable.ic_calendar),
                    label = stringResource(R.string.race_date),
                    value = details.date
                )
            }
        }
    }
}

@Composable
fun InfoRowWithIcon(
    icon: Painter,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
