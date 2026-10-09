package com.napcity.launcherstudio.data

import kotlinx.serialization.Serializable

/**
 * Versioned project schema for the Launcher Studio.
 * A project describes a complete custom launcher: theme, layout, drawer, dock.
 * Serializes to JSON for export/import/sharing.
 */
@Serializable
data class LauncherProject(
    val schemaVersion: Int = CURRENT_SCHEMA_VERSION,
    val id: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val theme: ThemeConfig = ThemeConfig(),
    val layout: LayoutConfig = LayoutConfig(),
    val drawer: DrawerConfig = DrawerConfig(),
    val dock: DockConfig = DockConfig(),
    val wallpaper: WallpaperConfig = WallpaperConfig(),
    val gestures: GestureConfig = GestureConfig(),
    val icons: IconConfig = IconConfig()
) {
    companion object {
        const val CURRENT_SCHEMA_VERSION = 2
    }
}

@Serializable
data class ThemeConfig(
    /** Palette name from the design library (e.g. "plasma_storm", "laser_grid") */
    val paletteName: String = "plasma_storm",
    /** Hex colors — override or extend the named palette */
    val primary: String = "#FF00D4",
    val secondary: String = "#00F5FF",
    val tertiary: String = "#00FFB3",
    val background: String = "#08001A",
    val surface: String = "#0D0D1A",
    val darkTheme: Boolean = true
)

@Serializable
data class LayoutConfig(
    /** Home grid columns (3-6) */
    val gridColumns: Int = 4,
    /** Icon size in dp (40-72) */
    val iconSizeDp: Int = 56,
    val showLabels: Boolean = true,
    /** Home layout style */
    val style: HomeStyle = HomeStyle.GRID
)

@Serializable
enum class HomeStyle {
    GRID,           // Classic icon grid
    LIST,           // Niagara-style vertical list
    MINIMAL         // Typography hero + few icons
}

@Serializable
data class DrawerConfig(
    val style: DrawerStyle = DrawerStyle.GRID,
    val gridColumns: Int = 4,
    val showSearch: Boolean = true
)

@Serializable
enum class DrawerStyle {
    GRID,
    LIST,
    CATEGORIZED   // Auto-categorized like Smart Launcher
}

@Serializable
data class DockConfig(
    val enabled: Boolean = true,
    /** Number of dock slots (3-7) */
    val slots: Int = 5,
    val style: DockStyle = DockStyle.BAR
)

@Serializable
enum class DockStyle {
    BAR,        // Classic bottom bar
    FLOATING,   // Floating pill
    HIDDEN      // Swipe-up only
}

@Serializable
data class WallpaperConfig(
    /** Drawable resource name (e.g. "wp_plasma_storm") or "none" for theme background */
    val wallpaperId: String = "wp_plasma_storm",
    /** Dim overlay 0-80% for readability */
    val dimPercent: Int = 20
)

@Serializable
data class GestureConfig(
    val doubleTapAction: GestureAction = GestureAction.LOCK_SCREEN,
    val swipeDownAction: GestureAction = GestureAction.OPEN_DRAWER,
    val swipeUpAction: GestureAction = GestureAction.OPEN_DRAWER
)

@Serializable
enum class GestureAction {
    NONE,
    LOCK_SCREEN,
    OPEN_DRAWER,
    OPEN_STUDIO
}

@Serializable
data class IconConfig(
    val shape: IconShape = IconShape.ROUNDED,
    /** Corner radius percent for ROUNDED (0-50) */
    val cornerRadius: Int = 28
)

@Serializable
enum class IconShape {
    CIRCLE,
    ROUNDED,
    SQUIRCLE,
    TEARDROP,
    SYSTEM      // No masking — app's own shape
}
