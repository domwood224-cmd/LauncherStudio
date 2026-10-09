package com.napcity.launcherstudio.data

/**
 * Built-in wallpapers generated for the Studio — one per palette.
 * Drawable resources in res/drawable-nodpi/wp_*.jpg
 */
object BuiltInWallpapers {

    data class Wallpaper(val id: String, val displayName: String)

    val all: List<Wallpaper> = listOf(
        Wallpaper("none", "None (theme background)"),
        Wallpaper("wp_plasma_storm", "Plasma Storm"),
        Wallpaper("wp_laser_grid", "Laser Grid"),
        Wallpaper("wp_flux", "Flux"),
        Wallpaper("wp_uv_spectrum", "UV Spectrum"),
        Wallpaper("wp_acid_rain", "Acid Rain"),
        Wallpaper("wp_neon_sunset", "Neon Sunset"),
        Wallpaper("wp_glitch", "Glitch"),
        Wallpaper("wp_neon_bubbles", "Neon Bubbles"),
        Wallpaper("wp_midnight_mono", "Midnight Mono"),
        Wallpaper("wp_royal_noir", "Royal Noir")
    )

    fun byId(id: String): Wallpaper = all.find { it.id == id } ?: all[0]
}
