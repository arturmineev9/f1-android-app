package ru.itis.f1app.presentation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.itis.f1app.feature.standings.impl.presentation.StandingsScreen

object StandingsTab : Tab {
    private const val TAB_INDEX: UShort = 1u

    override val options: TabOptions
        @Composable
        get() {
            val title = "Standings"
            val icon = rememberVectorPainter(Icons.Filled.Person)

            return remember {
                TabOptions(
                    index = TAB_INDEX,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        StandingsScreen().Content()
    }
}
