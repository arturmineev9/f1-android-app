package ru.itis.f1app.feature.auth.impl.presentation.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ru.itis.f1app.feature.auth.api.exception.AuthExceptions
import ru.itis.f1app.feature.auth.impl.R
import ru.itis.f1app.feature.auth.impl.presentation.viewmodel.AuthState

@Composable
internal fun RegisterContent(
    state: AuthState,
    onRegisterClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
    onValueChange: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        RegisterInputs(
            username = username,
            password = password,
            state = state,
            onUsernameChange = {
                username = it
                if (state.error != null) onValueChange()
            },
            onPasswordChange = {
                password = it
                if (state.error != null) onValueChange()
            }
        )

        ErrorMessage(state.error)

        Spacer(modifier = Modifier.height(24.dp))

        RegisterActions(
            isLoading = state.isLoading,
            onRegisterClick = { onRegisterClick(username, password) },
            onBackClick = onBackClick
        )
    }
}

@Composable
private fun RegisterInputs(
    username: String,
    password: String,
    state: AuthState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text(stringResource(R.string.label_username)) },
        isError = state.error is AuthExceptions.EmptyUsername || state.error is AuthExceptions.UserAlreadyExists,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(R.string.label_password_min)) },
        visualTransformation = PasswordVisualTransformation(),
        isError = state.error is AuthExceptions.ShortPassword,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ErrorMessage(error: AuthExceptions?) {
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

@Composable
private fun RegisterActions(
    isLoading: Boolean,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit
) {
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.register_button))
        }
    }

    TextButton(onClick = onBackClick) {
        Text(stringResource(R.string.have_account))
    }
}
