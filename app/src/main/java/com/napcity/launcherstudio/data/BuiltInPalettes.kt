package com.napcity.launcherstudio.data

/**
 * Built-in palettes from Dom's design library (~/workspace/design-library/themes.json).
 * Each maps to a ThemeConfig. More palettes can be added here as the library grows.
 */
object BuiltInPalettes {

    data class Palette(
        val id: String,
        val displayName: String,
        val description: String,
        val theme: ThemeConfig
    )

    val all: List<Palette> = listOf(
        Palette(
            "plasma_storm", "Plasma Storm",
            "Full-spectrum energy. No limits.",
            ThemeConfig("plasma_storm", "#FF00D4", "#0090FF", "#00FFB3", "#08001A", "#0D0D1A")
        ),
        Palette(
            "laser_grid", "Laser Grid",
            "Hyper-electric cyberpunk. Dare to glow.",
            ThemeConfig("laser_grid", "#FF0090", "#00F5FF", "#CCFF00", "#0D0D1A", "#12121F")
        ),
        Palette(
            "flux", "Flux",
            "Maximum color. Maximum voltage.",
            ThemeConfig("flux", "#FF2088", "#00FFD4", "#FFE800", "#050510", "#0A0A14")
        ),
        Palette(
            "uv_spectrum", "UV Spectrum",
            "The ultraviolet end of the spectrum.",
            ThemeConfig("uv_spectrum", "#DF00FF", "#00FFCC", "#FF0066", "#0A000F", "#100A18")
        ),
        Palette(
            "acid_rain", "Acid Rain",
            "A neon palette for the future.",
            ThemeConfig("acid_rain", "#FF00FF", "#00E5FF", "#FFFF00", "#0A0F1A", "#0D1420")
        ),
        Palette(
            "neon_sunset", "Neon Sunset",
            "Bold, bright, and unforgettable.",
            ThemeConfig("neon_sunset", "#FF0F7B", "#FF5C00", "#A8E000", "#0A0A12", "#14101A")
        ),
        Palette(
            "glitch", "Glitch Art",
            "Beauty in the error.",
            ThemeConfig("glitch", "#FF003C", "#00FFD0", "#8300FF", "#000820", "#0A1030")
        ),
        Palette(
            "neon_bubbles", "Neon Bubbles",
            "Bold. Bright. Playful.",
            ThemeConfig("neon_bubbles", "#FF2DA1", "#00C2FF", "#00E0D1", "#000000", "#0D0D0D")
        ),
        Palette(
            "midnight_mono", "Midnight Mono",
            "Clean monochrome with a single accent.",
            ThemeConfig("midnight_mono", "#FFFFFF", "#888888", "#FF003C", "#000000", "#111111")
        ),
        Palette(
            "royal_noir", "Royal Noir",
            "Purple reign. Dark luxury.",
            ThemeConfig("royal_noir", "#9000FF", "#BF94E4", "#DA70FA", "#0A0014", "#140A24")
        ),
        Palette(
            "cyber_mint", "Cyber Mint",
            "Fresh voltage. Clean and electric.",
            ThemeConfig("cyber_mint", "#00FFB3", "#00F5FF", "#FF00D4", "#02120C", "#0A1A14")
        ),
        Palette(
            "blood_moon", "Blood Moon",
            "Dark ritual. Red on black.",
            ThemeConfig("blood_moon", "#FF003C", "#FF5C00", "#FFD400", "#0D0208", "#1A0A10")
        ),
        Palette(
            "deep_ocean", "Deep Ocean",
            "Bioluminescent depths.",
            ThemeConfig("deep_ocean", "#00F5FF", "#0090FF", "#00FFB3", "#020A14", "#0A1420")
        ),
        Palette(
            "toxic", "Toxic",
            "Hazard stripes for your home screen.",
            ThemeConfig("toxic", "#CCFF00", "#00FF66", "#FF00FF", "#0A0F02", "#141A08")
        ),
        Palette(
            "candy", "Candy",
            "Sweet but dangerous.",
            ThemeConfig("candy", "#FF2DA1", "#FF6B9D", "#00E0D1", "#12040C", "#1E0A14")
        ),
        Palette(
            "ghost", "Ghost",
            "Pale signals in the dark.",
            ThemeConfig("ghost", "#E0E0FF", "#A0A0CC", "#00F5FF", "#080810", "#101018")
        )
    )

    fun byId(id: String): Palette = all.find { it.id == id } ?: all[0]
}
