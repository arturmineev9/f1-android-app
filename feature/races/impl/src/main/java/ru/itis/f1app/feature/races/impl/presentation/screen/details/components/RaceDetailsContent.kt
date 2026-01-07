package ru.itis.f1app.feature.races.impl.presentation.screen.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.itis.f1app.core.ui.theme.Bronze
import ru.itis.f1app.core.ui.theme.Gold
import ru.itis.f1app.core.ui.theme.Silver
import ru.itis.f1app.feature.races.api.domain.model.RaceDetails
import ru.itis.f1app.feature.races.api.domain.model.RaceResult
import ru.itis.f1app.feature.races.impl.R

private const val POS_1 = 1
private const val POS_2 = 2
private const val POS_3 = 3
private const val TOP_10 = 10

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
            ResultsHeader()
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
private fun ResultsHeader() {
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
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Text(
            text = stringResource(R.string.points),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )
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
        ResultPositionBadge(result.position)

        Spacer(modifier = Modifier.width(16.dp))

        ResultDriverInfo(
            modifier = Modifier.weight(1f),
            driverName = result.driverName,
            constructorName = result.constructorName,
            time = result.time
        )

        ResultPoints(
            position = result.position,
            points = result.points
        )
    }
}

@Composable
private fun ResultPositionBadge(position: Int) {
    Box(
        modifier = Modifier
            .width(32.dp)
            .height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        val positionColor = when (position) {
            POS_1 -> Gold
            POS_2 -> Silver
            POS_3 -> Bronze
            else -> Color.Transparent
        }

        if (position <= POS_3) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(positionColor.copy(alpha = 0.2f))
            )
        }

        Text(
            text = stringResource(R.string.result_position, position),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (position <= POS_3) FontWeight.Bold else FontWeight.Normal,
            color = if (position <= POS_3) positionColor else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ResultDriverInfo(
    modifier: Modifier,
    driverName: String,
    constructorName: String,
    time: String
) {
    Column(modifier = modifier) {
        Text(
            text = driverName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = constructorName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (time.isNotEmpty()) {
            Text(
                text = time,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun ResultPoints(position: Int, points: String) {
    val isPoints = position <= TOP_10
    Text(
        text = stringResource(R.string.result_points, points),
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = if (isPoints) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.width(40.dp),
        textAlign = TextAlign.End
    )
}
