package ru.itis.f1app.feature.drivers.impl.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

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
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = {
                        Text(
                            text = state.driverDetails?.fullName ?: "Driver Profile",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.onBackClicked() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (state.isLoading && state.driverDetails == null) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    state.driverDetails?.let { details ->
                        DriverDetailsContent(
                            state = details
                        )
                    }
                }
            }
        }
    }
}
