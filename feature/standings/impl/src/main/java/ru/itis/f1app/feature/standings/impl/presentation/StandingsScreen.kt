package ru.itis.f1app.feature.standings.impl.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.itis.f1app.feature.standings.impl.presentation.components.ConstructorsTable
import ru.itis.f1app.feature.standings.impl.presentation.components.DriversTable
import ru.itis.f1app.feature.standings.impl.presentation.mvi.StandingsSideEffect

class StandingsScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<StandingsViewModel>()
        val state by viewModel.container.stateFlow.collectAsState()
        //val navigator = LocalNavigator.currentOrThrow
        val snackBarHostState = remember { SnackbarHostState() }

        viewModel.collectSideEffect { effect ->
            when (effect) {
                is StandingsSideEffect.NavigateToDriverDetails -> {
                    // Пока просто лог или тост, так как модуль Drivers еще не готов
                    // navigator.push(DriverDetailsScreen(effect.driverId))
                    println("Navigate to driver: ${effect.driverId}")
                }
                is StandingsSideEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (state.isLoading && state.drivers.isEmpty() && state.constructors.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    StandingsContent(
                        selectedTab = state.selectedTab,
                        onTabSelected = viewModel::onTabSelected,
                        drivers = state.drivers,
                        constructors = state.constructors,
                        onDriverClick = viewModel::onDriverClicked
                    )
                }
            }
        }
    }
}

@Composable
fun StandingsContent(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    drivers: List<ru.itis.f1app.feature.standings.api.domain.model.DriverStanding>,
    constructors: List<ru.itis.f1app.feature.standings.api.domain.model.ConstructorStanding>,
    onDriverClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                text = { Text("Drivers") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                text = { Text("Teams") }
            )
        }

        when (selectedTab) {
            0 -> DriversTable(drivers = drivers, onDriverClick = onDriverClick)
            1 -> ConstructorsTable(teams = constructors)
        }
    }
}
