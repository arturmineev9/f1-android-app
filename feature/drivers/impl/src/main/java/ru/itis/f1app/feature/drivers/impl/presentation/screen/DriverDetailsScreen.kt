package ru.itis.f1app.feature.drivers.impl.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.itis.f1app.feature.drivers.impl.presentation.DriverDetailsViewModel
import ru.itis.f1app.feature.drivers.impl.presentation.mvi.DriverDetailsSideEffect

class DriverDetailsScreen(
    private val driverId: String,
    private val year: String = "current"
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<DriverDetailsViewModel>()
        val state by viewModel.container.stateFlow.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val snackBarHostState = remember { SnackbarHostState() }

        LaunchedEffect(driverId, year) {
            viewModel.loadDriverDetails(driverId, year)
        }

        viewModel.collectSideEffect { effect ->
            when (effect) {
                is DriverDetailsSideEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
                DriverDetailsSideEffect.NavigateBack -> {
                    navigator.pop()
                }
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = state.driverDetails?.fullName ?: "Driver",
                            maxLines = 1
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.onBackClicked() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { paddingValues ->
            if (state.isLoading && state.driverDetails == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                state.driverDetails?.let { details ->
                    DriverDetailsContent(
                        modifier = Modifier.padding(paddingValues),
                        state = details
                    )
                }
            }
        }
    }
}
