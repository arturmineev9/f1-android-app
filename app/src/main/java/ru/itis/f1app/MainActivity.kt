package ru.itis.f1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.f1app.core.navigation.SharedScreens
import ru.itis.f1app.core.ui.theme.F1AppTheme
import ru.itis.f1app.feature.auth.impl.presentation.screen.login.LoginScreen
import ru.itis.f1app.feature.auth.impl.presentation.screen.register.RegisterScreen
import ru.itis.f1app.feature.drivers.impl.presentation.screen.DriverDetailsScreen
import ru.itis.f1app.feature.races.impl.presentation.screen.races.RacesScreen
import ru.itis.f1app.feature.standings.impl.presentation.StandingsScreen
import ru.itis.f1app.presentation.MainScreen
import ru.itis.f1app.presentation.RootScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ScreenRegistry {
            register<SharedScreens.Login> { LoginScreen() }
            register<SharedScreens.Register> { RegisterScreen() }

            register<SharedScreens.Main> { MainScreen() }

            register<SharedScreens.Races> { RacesScreen() }
            register<SharedScreens.Standings> { StandingsScreen() }

            register<SharedScreens.DriverDetails> { screen ->
                DriverDetailsScreen(screen.driverId, screen.year)
            }
        }

        setContent {
            F1AppTheme {
                Navigator(RootScreen())
            }
        }
    }
}
