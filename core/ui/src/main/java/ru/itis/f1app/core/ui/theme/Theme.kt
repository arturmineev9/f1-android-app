package ru.itis.f1app.core.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = F1Red,
    onPrimary = White,
    primaryContainer = F1RedDark,
    onPrimaryContainer = White,

    secondary = GrayLight,
    onSecondary = CarbonBackground,
    secondaryContainer = CarbonSurfaceVariant,
    onSecondaryContainer = White,

    tertiary = F1Red,
    onTertiary = White,

    background = CarbonBackground,
    onBackground = White,

    surface = CarbonSurface,
    onSurface = White,

    surfaceVariant = CarbonSurfaceVariant,
    onSurfaceVariant = GrayLight,

    error = F1Red,
    onError = White
)

private val LightColorScheme = lightColorScheme(
    primary = F1Red,
    background = LightBackground,
    surface = LightSurface
)

@Composable
fun F1AppTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            @Suppress("DEPRECATION")
            run {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
            }
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
