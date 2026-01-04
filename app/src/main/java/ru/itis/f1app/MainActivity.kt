package ru.itis.f1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.f1app.feature.auth.impl.presentation.screen.login.LoginScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigator(LoginScreen())
        }
    }
}
