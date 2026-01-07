package ru.itis.f1app.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.itis.f1app.core.ui.theme.CarbonSurfaceVariant
import ru.itis.f1app.core.ui.theme.F1Red
import ru.itis.f1app.core.ui.theme.White
import ru.itis.f1app.presentation.tabs.RacesTab
import ru.itis.f1app.presentation.tabs.StandingsTab

class MainScreen : Screen {
    @Composable
    override fun Content() {
        TabNavigator(RacesTab) {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = CarbonSurfaceVariant,
                        contentColor = White
                    ) {
                        TabNavigationItem(RacesTab)
                        TabNavigationItem(StandingsTab)
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    CurrentTab()
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab

    NavigationBarItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title
                )
            }
        },
        label = {
            Text(
                text = tab.options.title,
                style = if (isSelected) MaterialTheme.typography.labelLarge else MaterialTheme.typography.labelMedium
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = F1Red,
            selectedIconColor = White,
            selectedTextColor = White,

            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
