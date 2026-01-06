package ru.itis.f1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.f1app.core.navigation.SharedScreens
import ru.itis.f1app.feature.auth.impl.presentation.screen.login.LoginScreen
import ru.itis.f1app.feature.auth.impl.presentation.screen.register.RegisterScreen
import ru.itis.f1app.feature.races.impl.presentation.screen.races.RacesScreen
import ru.itis.f1app.presentation.RootScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ScreenRegistry {
            register<SharedScreens.Login> { LoginScreen() }
            register<SharedScreens.Races> { RacesScreen() }
            register<SharedScreens.Register> { RegisterScreen() }
        }

        setContent {
            Navigator(RootScreen())
        }
    }
}
