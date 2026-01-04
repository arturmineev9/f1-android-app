package ru.itis.f1app.feature.races.impl.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.races.api.domain.exception.RacesExceptions
import ru.itis.f1app.feature.races.impl.R

@Composable
internal fun RacesErrorScreen(
    error: RacesExceptions,
    onRetry: () -> Unit
) {
    val errorText = when (error) {
        is RacesExceptions.NetworkConnection -> stringResource(R.string.error_network_connection)
        is RacesExceptions.NoDataAvailable -> stringResource(R.string.error_no_data)
        is RacesExceptions.ServerError -> stringResource(R.string.error_server, error.code)
        else -> stringResource(R.string.error_unknown)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}
