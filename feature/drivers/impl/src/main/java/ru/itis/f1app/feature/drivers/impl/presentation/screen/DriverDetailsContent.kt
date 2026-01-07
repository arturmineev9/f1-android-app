package ru.itis.f1app.feature.drivers.impl.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.f1app.core.ui.theme.Bronze
import ru.itis.f1app.core.ui.theme.Gold
import ru.itis.f1app.core.ui.theme.Silver
import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails
import ru.itis.f1app.feature.drivers.api.domain.model.DriverRaceResult
import ru.itis.f1app.feature.drivers.impl.R
import java.util.Locale

private const val POS_1 = 1
private const val POS_2 = 2
private const val POS_3 = 3
private const val POS_DEFAULT = 99
@Composable
fun DriverDetailsContent(
    modifier: Modifier = Modifier,
    state: DriverDetails
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { DriverInfoCard(state) }
        item { DriverStatsRow(state) }

        item {
            Text(
                text = stringResource(R.string.driver_details_recent_races),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = SpacerSmall)
            )
        }

        items(state.recentResults) { result ->
            DriverRaceResultItem(result)
        }
    }
}

@Composable
private fun DriverInfoCard(details: DriverDetails) {
    Card(
        shape = RoundedCornerShape(CardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(CardPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = details.fullName,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    lineHeight = 32.sp
                )
                Spacer(modifier = Modifier.height(SpacerMedium))

                DriverDetailRow(
                    icon = painterResource(id = ru.itis.f1app.core.ui.R.drawable.ic_groups),
                    text = details.teamName ?: stringResource(R.string.driver_no_team)
                )

                Spacer(modifier = Modifier.height(SpacerSmall))
                DriverDetailRow(
                    icon = painterResource(id = ru.itis.f1app.core.ui.R.drawable.ic_flag),
                    text = stringResource(R.string.driver_details_nationality, details.nationality)
                )

                Spacer(modifier = Modifier.height(SpacerSmall))
                DriverDetailRow(
                    icon = painterResource(id = ru.itis.f1app.core.ui.R.drawable.ic_cake),
                    text = stringResource(R.string.driver_details_birth_date, details.birthDate)
                )
            }

            details.number?.let { number ->
                DriverNumberBadge(number)
            }
        }
    }
}

@Composable
private fun DriverNumberBadge(number: String) {
    Box(
        modifier = Modifier
            .size(NumberBadgeSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic
            ),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun DriverDetailRow(
    icon: Painter,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(IconSizeSmall)
        )
        Spacer(modifier = Modifier.width(SpacerSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DriverStatsRow(details: DriverDetails) {
    val totalPoints = details.recentResults.sumOf { it.points }
    val racesCount = details.recentResults.size
    val podiums = details.recentResults.count {
        it.position.toIntOrNull()?.let { pos -> pos <= POS_3 } == true
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(
            label = stringResource(R.string.stat_points),
            value = String.format(Locale.US, "%.0f", totalPoints)
        )
        VerticalDivider(modifier = Modifier.height(40.dp))
        StatItem(
            label = stringResource(R.string.stat_races),
            value = racesCount.toString()
        )
        VerticalDivider(modifier = Modifier.height(40.dp))
        StatItem(
            label = stringResource(R.string.stat_podiums),
            value = podiums.toString()
        )
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun DriverRaceResultItem(result: DriverRaceResult) {
    val posInt = result.position.toIntOrNull() ?: POS_DEFAULT
    val isPodium = posInt <= POS_3

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpacerSmall)
            .height(IntrinsicSize.Min)
    ) {
        ResultPositionBadge(result.position, isPodium, posInt)

        Spacer(modifier = Modifier.width(SpacerMedium))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = result.raceName,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                maxLines = 1
            )

            Text(
                text = stringResource(R.string.driver_race_round_date, result.raceRound, result.date),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        ResultPointsBadge(result.points)
    }
}

@Composable
private fun ResultPositionBadge(position: String, isPodium: Boolean, posInt: Int) {
    val posColor = when (posInt) {
        POS_1 -> Gold
        POS_2 -> Silver
        POS_3 -> Bronze
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Box(
        modifier = Modifier
            .width(PositionBadgeWidth)
            .fillMaxHeight()
            .clip(RoundedCornerShape(PositionBadgeRadius))
            .background(if (isPodium) posColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = position,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = if (isPodium) posColor else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ResultPointsBadge(points: Double) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            val pointsFormatted = String.format(Locale.US, "%.0f", points)

            Text(
                text = stringResource(R.string.driver_race_points_plus, pointsFormatted),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private val CardCornerRadius = 16.dp
private val CardPadding = 24.dp
private val SpacerSmall = 8.dp
private val SpacerMedium = 16.dp
private val NumberBadgeSize = 80.dp
private val IconSizeSmall = 18.dp
private val PositionBadgeWidth = 56.dp
private val PositionBadgeRadius = 8.dp
