package com.napcity.launcherstudio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Base colors drawn from Dom's design library — Plasma Storm / Laser Grid family
val NearBlack = Color(0xFF08001A)
val HotMagenta = Color(0xFFFF00D4)
val ElectricBlue = Color(0xFF0090FF)
val NeonOrange = Color(0xFFFF6200)
val SpringNeon = Color(0xFF00FFB3)
val LaserCyan = Color(0xFF00F5FF)
val HotPink = Color(0xFFFF0090)
val VoltGreen = Color(0xFFCCFF00)
val DeepVoid = Color(0xFF0D0D1A)

private val DarkColorScheme = darkColorScheme(
    primary = HotMagenta,
    secondary = LaserCyan,
    tertiary = SpringNeon,
    background = NearBlack,
    surface = DeepVoid,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = HotMagenta,
    secondary = ElectricBlue,
    tertiary = SpringNeon
)

@Composable
fun LauncherStudioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
