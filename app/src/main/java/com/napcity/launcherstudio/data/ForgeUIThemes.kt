package com.napcity.launcherstudio.data

/**
 * ForgeUI curated themes (v0.20.0) — 66 hand-tuned themes across 14 families.
 * Imported from ForgeUI. Each maps to a launcher ThemeConfig.
 */
object ForgeUIThemes {

    data class ForgeTheme(
        val id: String,
        val displayName: String,
        val family: String,
        val theme: ThemeConfig
    )

    val all: List<ForgeTheme> = listOf(
        ForgeTheme(
            "horror-midnight", "Midnight Dread", "horror",
            ThemeConfig("horror-midnight", "#FFB4AB", "#FF8A7B", "#FF6B5B", "#0A0A0C", "#141418")
        ),
        ForgeTheme(
            "horror-bloodline", "Bloodline", "horror",
            ThemeConfig("horror-bloodline", "#FF1744", "#B71C1C", "#7F0000", "#08080A", "#121215")
        ),
        ForgeTheme(
            "horror-static", "Dead Channel", "horror",
            ThemeConfig("horror-static", "#9E9E9E", "#616161", "#BDBDBD", "#050505", "#0D0D0D")
        ),
        ForgeTheme(
            "horror-ritual", "Ritual", "horror",
            ThemeConfig("horror-ritual", "#CE93D8", "#7B1FA2", "#4A148C", "#0B0710", "#140F18")
        ),
        ForgeTheme(
            "neon-abyss", "Neon Abyss", "neon",
            ThemeConfig("neon-abyss", "#00F0FF", "#FF2EA6", "#7B2EFF", "#05010D", "#0D0A1A")
        ),
        ForgeTheme(
            "neon-venom", "Venom", "neon",
            ThemeConfig("neon-venom", "#39FF14", "#FFE600", "#00E5FF", "#030A03", "#0A120A")
        ),
        ForgeTheme(
            "neon-geisha", "Geisha", "neon",
            ThemeConfig("neon-geisha", "#FF4D8D", "#4DC9FF", "#B44DFF", "#0D0509", "#170D12")
        ),
        ForgeTheme(
            "glossy-magenta", "Glossy Magenta", "glossy",
            ThemeConfig("glossy-magenta", "#FF2EA6", "#B44DFF", "#FF8AC2", "#070509", "#120B14")
        ),
        ForgeTheme(
            "jewel-ruby", "Ruby", "jewel",
            ThemeConfig("jewel-ruby", "#FF2A5C", "#FFD700", "#FF8FA3", "#0E0709", "#170C10")
        ),
        ForgeTheme(
            "jewel-emerald", "Emerald", "jewel",
            ThemeConfig("jewel-emerald", "#00E676", "#FFD700", "#69F0AE", "#060D08", "#0C1710")
        ),
        ForgeTheme(
            "jewel-sapphire", "Sapphire", "jewel",
            ThemeConfig("jewel-sapphire", "#3B82F6", "#C0C0C0", "#93C5FD", "#060A12", "#0C1420")
        ),
        ForgeTheme(
            "jewel-amethyst", "Amethyst", "jewel",
            ThemeConfig("jewel-amethyst", "#A855F7", "#E9D5FF", "#D8B4FE", "#0C0714", "#150C22")
        ),
        ForgeTheme(
            "jewel-topaz", "Topaz", "jewel",
            ThemeConfig("jewel-topaz", "#FFB627", "#FFF3D6", "#FFD97D", "#0E0A05", "#181006")
        ),
        ForgeTheme(
            "jewel-onyx", "Onyx", "jewel",
            ThemeConfig("jewel-onyx", "#E8E8EC", "#71717A", "#A1A1AA", "#050506", "#0C0C0E")
        ),
        ForgeTheme(
            "jewel-gold", "Gold", "jewel",
            ThemeConfig("jewel-gold", "#FFD700", "#FFF3C4", "#FFEA94", "#0D0A04", "#141005")
        ),
        ForgeTheme(
            "jewel-garnet", "Garnet", "jewel",
            ThemeConfig("jewel-garnet", "#D7263D", "#FFD700", "#F4606E", "#0E0507", "#180A0D")
        ),
        ForgeTheme(
            "jewel-jade", "Jade", "jewel",
            ThemeConfig("jewel-jade", "#00A86B", "#C0C0C0", "#5EEAD4", "#04100B", "#0A1A13")
        ),
        ForgeTheme(
            "jewel-opal", "Opal", "jewel",
            ThemeConfig("jewel-opal", "#A8C8E8", "#F9C8E8", "#C8F9E8", "#080B10", "#0E141C")
        ),
        ForgeTheme(
            "jewel-turquoise", "Turquoise", "jewel",
            ThemeConfig("jewel-turquoise", "#40E0D0", "#F7E7CE", "#7DEEE2", "#041210", "#0A1D1B")
        ),
        ForgeTheme(
            "jewel-royal", "Royal Purple", "jewel",
            ThemeConfig("jewel-royal", "#8B5CF6", "#FFD700", "#C4B5FD", "#0A0614", "#120B22")
        ),
        ForgeTheme(
            "aethereal-blueprint", "Aethereal Blueprint", "aethereal",
            ThemeConfig("aethereal-blueprint", "#2DE1C2", "#E8862E", "#7DD3FC", "#04202B", "#062A38")
        ),
        ForgeTheme(
            "nocturne-violet", "Nocturne Violet", "nocturne",
            ThemeConfig("nocturne-violet", "#A855F7", "#E9D5FF", "#6D28D9", "#0B0612", "#140B1E")
        ),
        ForgeTheme(
            "neon-crimson", "Neon Crimson", "neon",
            ThemeConfig("neon-crimson", "#FF2500", "#FF0800", "#ED2939", "#0D0505", "#150807")
        ),
        ForgeTheme(
            "neon-lime", "Neon Lime", "neon",
            ThemeConfig("neon-lime", "#37FD12", "#C7EA46", "#7FFF00", "#060D05", "#0B1508")
        ),
        ForgeTheme(
            "mono-ink", "Mono Ink", "mono",
            ThemeConfig("mono-ink", "#FFFFFF", "#8A8A8A", "#3A3A3A", "#000000", "#0A0A0A")
        ),
        ForgeTheme(
            "retro-vhs", "Tracking…", "retro",
            ThemeConfig("retro-vhs", "#FFFFFF", "#00FF87", "#FFB300", "#0A0A0A", "#111111")
        ),
        ForgeTheme(
            "retro-synthwave", "Midnight Drive", "retro",
            ThemeConfig("retro-synthwave", "#FF71CE", "#01CDFE", "#B967FF", "#0D0221", "#1A0B2E")
        ),
        ForgeTheme(
            "retro-terminal", "Phosphor", "retro",
            ThemeConfig("retro-terminal", "#33FF33", "#FFB000", "#00CCCC", "#000A00", "#001400")
        ),
        ForgeTheme(
            "minimal-bone", "Bone", "minimal",
            ThemeConfig("minimal-bone", "#1A1A1A", "#6B6B6B", "#B0B0B0", "#FAFAF8", "#FFFFFF")
        ),
        ForgeTheme(
            "minimal-ink", "Ink", "minimal",
            ThemeConfig("minimal-ink", "#F5F5F0", "#8E8E93", "#48484A", "#0A0A0A", "#141414")
        ),
        ForgeTheme(
            "minimal-paper", "Paper Crane", "minimal",
            ThemeConfig("minimal-paper", "#B7410E", "#2E5E4E", "#E3A72F", "#FDFBF7", "#FFFFFF")
        ),
        ForgeTheme(
            "royal-obsidian", "Obsidian Court", "royal",
            ThemeConfig("royal-obsidian", "#D4AF37", "#8E6E2F", "#5C4A1E", "#0C0A06", "#14100A")
        ),
        ForgeTheme(
            "royal-velvet", "Velvet", "royal",
            ThemeConfig("royal-velvet", "#E1BEE7", "#AD1457", "#6A1B9A", "#120710", "#1D0F16")
        ),
        ForgeTheme(
            "nature-moss", "Moss", "nature",
            ThemeConfig("nature-moss", "#9CCC65", "#4C7C4C", "#D7CCC8", "#0B0F08", "#12170F")
        ),
        ForgeTheme(
            "nature-abyssal", "Abyssal", "nature",
            ThemeConfig("nature-abyssal", "#4DD0E1", "#00695C", "#004D40", "#041014", "#0A1A1E")
        ),
        ForgeTheme(
            "brutal-concrete", "Concrete", "brutal",
            ThemeConfig("brutal-concrete", "#000000", "#FF3B30", "#000000", "#E8E8E8", "#D8D8D8")
        ),
        ForgeTheme(
            "brutal-hazard", "Hazard", "brutal",
            ThemeConfig("brutal-hazard", "#FFD60A", "#FF3B30", "#FFD60A", "#0E0C05", "#161206")
        ),
        ForgeTheme(
            "dream-lavender", "Lavender Haze", "dream",
            ThemeConfig("dream-lavender", "#B388FF", "#80DEEA", "#FF8A80", "#0F0A18", "#171126")
        ),
        ForgeTheme(
            "material-chrome", "Liquid Chrome", "material",
            ThemeConfig("material-chrome", "#E8EAEE", "#9AA0AA", "#C9CDD4", "#0C0E11", "#14171B")
        ),
        ForgeTheme(
            "material-gold", "Molten Gold", "material",
            ThemeConfig("material-gold", "#D4AF37", "#8E6E2F", "#F9E7B2", "#0E0A05", "#161006")
        ),
        ForgeTheme(
            "material-copper", "Copper Wire", "material",
            ThemeConfig("material-copper", "#B87333", "#8C4A1F", "#F0B48A", "#0D0705", "#140C07")
        ),
        ForgeTheme(
            "material-titanium", "Titanium", "material",
            ThemeConfig("material-titanium", "#C2C4CC", "#7E828C", "#E8E8EC", "#0A0B0D", "#111316")
        ),
        ForgeTheme(
            "material-carbon", "Carbon Weave", "material",
            ThemeConfig("material-carbon", "#3A3D44", "#1C1E22", "#5A5E66", "#060607", "#0C0D0F")
        ),
        ForgeTheme(
            "material-oilslick", "Oil Slick", "material",
            ThemeConfig("material-oilslick", "#7B2EFF", "#00E5FF", "#FF2EA6", "#070409", "#0D0912")
        ),
        ForgeTheme(
            "ref-toxic", "Toxic Slime", "reference",
            ThemeConfig("ref-toxic", "#39FF14", "#8AFF5A", "#B6FF00", "#030603", "#0A120A")
        ),
        ForgeTheme(
            "ref-biohazard", "Biohazard", "reference",
            ThemeConfig("ref-biohazard", "#FF1744", "#FF5252", "#FF8A80", "#0A0304", "#120607")
        ),
        ForgeTheme(
            "ref-cyberrain", "Cyber Rain", "reference",
            ThemeConfig("ref-cyberrain", "#00E5FF", "#FF2EA6", "#7B2EFF", "#04070C", "#0A1218")
        ),
        ForgeTheme(
            "ref-coven", "Coven", "reference",
            ThemeConfig("ref-coven", "#C62828", "#D4AF37", "#8E2424", "#0D0508", "#140A10")
        ),
        ForgeTheme(
            "ref-royalstudio", "Royal Studio", "reference",
            ThemeConfig("ref-royalstudio", "#D4AF37", "#B388FF", "#FFF0B3", "#12081F", "#1A0E28")
        ),
        ForgeTheme(
            "ref-neondrip", "Neon Drip", "reference",
            ThemeConfig("ref-neondrip", "#FF2EA6", "#00E5FF", "#B388FF", "#08030C", "#100612")
        ),
        ForgeTheme(
            "ref-banking", "Midnight Banking", "reference",
            ThemeConfig("ref-banking", "#8B5CF6", "#22D3EE", "#FF8A3D", "#0A0E1A", "#12101E")
        ),
        ForgeTheme(
            "ref-watch", "Watch Black", "reference",
            ThemeConfig("ref-watch", "#4CAF50", "#FF9800", "#E91E63", "#000000", "#0A0A0A")
        ),
        ForgeTheme(
            "pal-nocturne", "Nocturne Bloom", "palette",
            ThemeConfig("pal-nocturne", "#7E4A68", "#B79AB4", "#4B2A46", "#1A1026", "#241A33")
        ),
        ForgeTheme(
            "pal-dune", "Ethereal Dune", "palette",
            ThemeConfig("pal-dune", "#6B705C", "#C4A48A", "#A8A87E", "#F4EFEB", "#EDE6DA")
        ),
        ForgeTheme(
            "pal-lagoon", "Astral Lagoon", "palette",
            ThemeConfig("pal-lagoon", "#4CA7A1", "#A6D9C9", "#1F5F63", "#0C2836", "#122F3D")
        ),
        ForgeTheme(
            "pal-crimson", "Crimson Haze", "palette",
            ThemeConfig("pal-crimson", "#E93B57", "#C77D88", "#6E1029", "#2B0A12", "#3A0F1A")
        ),
        ForgeTheme(
            "pal-jade", "Jade Whisper", "palette",
            ThemeConfig("pal-jade", "#3D6B5C", "#7AA897", "#B6D6C7", "#ECF3E9", "#DDE9E2")
        ),
        ForgeTheme(
            "pal-velvet", "Velvet Twilight", "palette",
            ThemeConfig("pal-velvet", "#6B3F7C", "#A76BA6", "#3A2257", "#1A1331", "#241A40")
        ),
        ForgeTheme(
            "pal-amber", "Amber Mirage", "palette",
            ThemeConfig("pal-amber", "#D99A4E", "#EBC28C", "#7A3D1E", "#FFF3E0", "#FBE8CF")
        ),
        ForgeTheme(
            "pal-sakura", "Midnight Sakura", "palette",
            ThemeConfig("pal-sakura", "#B47A9A", "#5E3A5C", "#2C2F3F", "#0B0E1A", "#141824")
        ),
        ForgeTheme(
            "pal-clayday", "Clay Day", "palette",
            ThemeConfig("pal-clayday", "#5B8DEF", "#7FD1C0", "#B9A7E6", "#DDE4EE", "#E8EDF5")
        ),
        ForgeTheme(
            "duo-emerald", "Emerald Depth", "duo",
            ThemeConfig("duo-emerald", "#C50337", "#021C4F", "#7A0A24", "#0A0F24", "#121A36")
        ),
        ForgeTheme(
            "duo-electric", "Electric Blue", "duo",
            ThemeConfig("duo-electric", "#00EEFF", "#0000FF", "#7A00FF", "#000A1A", "#0A1430")
        ),
        ForgeTheme(
            "duo-purplenight", "Purple Night", "duo",
            ThemeConfig("duo-purplenight", "#C200FB", "#7A00C8", "#00FFD4", "#00120B", "#0A1E14")
        ),
        ForgeTheme(
            "cyber-glitch", "Glitch Art", "cyber",
            ThemeConfig("cyber-glitch", "#FF003C", "#00FFD0", "#8300FF", "#000820", "#0A1030")
        ),
        ForgeTheme(
            "cyber-flux", "Flux Voltage", "cyber",
            ThemeConfig("cyber-flux", "#00FFD4", "#CC00FF", "#FF2088", "#050510", "#0D0D20")
        ),
    )

    val families: List<String> = all.map { it.family }.distinct()

    fun byFamily(family: String): List<ForgeTheme> = all.filter { it.family == family }
    fun byId(id: String): ForgeTheme? = all.find { it.id == id }
}