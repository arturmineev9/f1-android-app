package ru.itis.f1app.feature.standings.impl.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding

@Composable
fun ConstructorsTable(teams: List<ConstructorStanding>) {
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
                Text("Team", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text(
                    "Wins",
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Pts",
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider()
        }

        items(teams) { team ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(team.position.toString(), modifier = Modifier.width(30.dp))
                Text(team.teamName, modifier = Modifier.weight(1f))
                Text(
                    team.wins.toString(),
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    team.points.toString(),
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        }
    }
}
