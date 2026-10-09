package com.napcity.launcherstudio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.napcity.launcherstudio.data.ThemeConfig

/** Parse "#RRGGBB" or "#AARRGGBB" into a Compose Color */
fun String.toComposeColor(): Color = try {
    Color(android.graphics.Color.parseColor(this))
} catch (_: Exception) {
    Color.White
}

/**
 * Dynamic theme driven by a LauncherProject's ThemeConfig.
 * Falls back to system dark/light when no project is active.
 */
@Composable
fun LauncherStudioTheme(
    theme: ThemeConfig? = null,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (theme != null) {
        val useDark = theme.darkTheme
        if (useDark) {
            darkColorScheme(
                primary = theme.primary.toComposeColor(),
                secondary = theme.secondary.toComposeColor(),
                tertiary = theme.tertiary.toComposeColor(),
                background = theme.background.toComposeColor(),
                surface = theme.surface.toComposeColor(),
                onPrimary = Color.White,
                onSecondary = Color.Black,
                onBackground = Color.White,
                onSurface = Color.White
            )
        } else {
            lightColorScheme(
                primary = theme.primary.toComposeColor(),
                secondary = theme.secondary.toComposeColor(),
                tertiary = theme.tertiary.toComposeColor()
            )
        }
    } else {
        // Legacy fallback — Plasma Storm
        if (darkTheme) {
            darkColorScheme(
                primary = Color(0xFFFF00D4),
                secondary = Color(0xFF00F5FF),
                tertiary = Color(0xFF00FFB3),
                background = Color(0xFF08001A),
                surface = Color(0xFF0D0D1A),
                onPrimary = Color.White,
                onSecondary = Color.Black,
                onBackground = Color.White,
                onSurface = Color.White
            )
        } else {
            lightColorScheme(
                primary = Color(0xFFFF00D4),
                secondary = Color(0xFF0090FF),
                tertiary = Color(0xFF00FFB3)
            )
        }
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}
