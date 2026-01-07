package ru.itis.f1app.feature.standings.impl.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.itis.f1app.core.ui.theme.Bronze
import ru.itis.f1app.core.ui.theme.Gold
import ru.itis.f1app.core.ui.theme.Silver
import ru.itis.f1app.feature.standings.api.domain.model.DriverStanding

@Composable
fun DriversTable(
    drivers: List<DriverStanding>,
    onDriverClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("ПОЗ", modifier = Modifier.width(40.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                Text("ПИЛОТ", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                Text("КОМАНДА", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                Text("ОЧКИ", modifier = Modifier.width(50.dp), textAlign = TextAlign.End, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            }
        }

        items(drivers) { driver ->
            DriverRow(driver = driver, onClick = { onDriverClick(driver.driverId) })
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f), modifier = Modifier.padding(start = 16.dp))
        }
    }
}

@Composable
private fun DriverRow(driver: DriverStanding, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val posColor = when (driver.position) {
            1 -> Gold
            2 -> Silver
            3 -> Bronze
            else -> Color.Transparent
        }

        Box(
            modifier = Modifier
                .width(32.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if(driver.position <= 3) posColor.copy(alpha = 0.2f) else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = driver.position.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = if (driver.position <= 3) posColor else MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${driver.driverName} ${driver.driverSurname}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        Text(
            text = driver.teamName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )

        Text(
            text = String.format("%.0f", driver.points),
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
