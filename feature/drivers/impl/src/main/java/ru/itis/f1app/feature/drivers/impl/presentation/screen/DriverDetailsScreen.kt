package ru.itis.f1app.feature.drivers.impl.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import ru.itis.f1app.feature.drivers.impl.presentation.mvi.DriverDetailsState

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
                is DriverDetailsSideEffect.ShowError -> snackBarHostState.showSnackbar(effect.message)
                DriverDetailsSideEffect.NavigateBack -> navigator.pop()
            }
        }

        DriverDetailsScaffold(
            state = state,
            snackBarHostState = snackBarHostState,
            scrollBehavior = scrollBehavior,
            onBackClick = viewModel::onBackClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DriverDetailsScaffold(
    state: DriverDetailsState,
    snackBarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit
) {
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
                    IconButton(onClick = onBackClick) {
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
                    DriverDetailsContent(state = details)
                }
            }
        }
    }
}
