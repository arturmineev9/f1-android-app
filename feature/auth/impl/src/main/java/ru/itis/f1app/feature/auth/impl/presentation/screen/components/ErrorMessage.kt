package ru.itis.f1app.feature.auth.impl.presentation.screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.auth.api.exception.AuthExceptions
import ru.itis.f1app.feature.auth.impl.R

@Composable
fun ErrorMessage(error: AuthExceptions?) {
    if (error != null) {
        val errorText = when (error) {
            is AuthExceptions.EmptyUsername -> stringResource(R.string.error_empty_username)
            is AuthExceptions.ShortPassword -> stringResource(R.string.error_short_password)
            is AuthExceptions.UserAlreadyExists -> stringResource(R.string.error_user_exists)
            else -> stringResource(R.string.error_unknown)
        }

        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
