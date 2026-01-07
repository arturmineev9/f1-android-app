package ru.itis.f1app.feature.races.impl.presentation.screen.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.races.api.domain.model.RaceDetails
import ru.itis.f1app.feature.races.api.domain.model.RaceResult
import ru.itis.f1app.feature.races.impl.R
import ru.itis.f1app.core.ui.theme.Bronze
import ru.itis.f1app.core.ui.theme.Gold
import ru.itis.f1app.core.ui.theme.Silver

@Composable
fun RaceDetailsContent(
    details: RaceDetails,
    modifier: Modifier = Modifier,
    onDriverClick: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.results_tab) to Icons.Default.List,
        stringResource(R.string.info_tab) to Icons.Default.Info
    )

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            tabs.forEachIndexed { index, (title, icon) ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) },
                    icon = { Icon(icon, contentDescription = null) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        when (selectedTab) {
            0 -> RaceResultsList(
                results = details.results,
                onDriverClick = onDriverClick
            )

            1 -> RaceInfo(details)
        }
    }
}

@Composable
fun RaceResultsList(
    results: List<RaceResult>,
    onDriverClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.position),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.width(32.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.driver),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.points),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.End
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        }

        items(results) { result ->
            ResultItem(
                result = result,
                onClick = { onDriverClick(result.driverId) }
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(start = 50.dp)
            )
        }
    }
}

@Composable
fun ResultItem(
    result: RaceResult,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp),
            contentAlignment = Alignment.Center
        ) {
            val positionColor = when (result.position) {
                1 -> Gold
                2 -> Silver
                3 -> Bronze
                else -> Color.Transparent
            }

            if (result.position <= 3) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(positionColor.copy(alpha = 0.2f))
                )
            }

            Text(
                text = stringResource(R.string.result_position, result.position),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (result.position <= 3) FontWeight.Bold else FontWeight.Normal,
                color = if (result.position <= 3) positionColor else MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = result.driverName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = result.constructorName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (result.time.isNotEmpty()) {
                Text(
                    text = result.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        val isPoints = result.position <= 10
        Text(
            text = stringResource(R.string.result_points, result.points),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = if (isPoints) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.5f
            ),
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )
    }
}
