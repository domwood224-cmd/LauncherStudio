package com.napcity.launcherstudio.ui.studio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.napcity.launcherstudio.data.BuiltInPalettes
import com.napcity.launcherstudio.data.ForgeUIThemes
import com.napcity.launcherstudio.data.LauncherProject
import com.napcity.launcherstudio.data.ThemeConfig
import com.napcity.launcherstudio.ui.theme.toComposeColor

/**
 * Theme picker — Studio palettes + all 66 ForgeUI curated themes, grouped by family.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemePickerTab(
    project: LauncherProject,
    onThemeChange: (LauncherProject) -> Unit
) {
    var source by remember { mutableStateOf(0) } // 0 = Studio, 1 = ForgeUI

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = source) {
            Tab(selected = source == 0, onClick = { source = 0 }, text = { Text("Studio (16)") })
            Tab(selected = source == 1, onClick = { source = 1 }, text = { Text("ForgeUI (66)") })
        }

        if (source == 0) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(BuiltInPalettes.all) { palette ->
                    PaletteCard(
                        name = palette.displayName,
                        blurb = palette.description,
                        theme = palette.theme,
                        selected = project.theme.paletteName == palette.id,
                        onClick = { onThemeChange(project.copy(theme = palette.theme)) }
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                ForgeUIThemes.families.forEach { family ->
                    item {
                        Text(
                            family.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    item {
                        com.napcity.launcherstudio.ui.studio.ForgeThemeGrid(
                            themes = ForgeUIThemes.byFamily(family),
                            project = project,
                            onThemeChange = onThemeChange
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ForgeThemeGrid(
    themes: List<ForgeUIThemes.ForgeTheme>,
    project: LauncherProject,
    onThemeChange: (LauncherProject) -> Unit
) {
    // Manual 2-column grid inside LazyColumn
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        themes.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { ft ->
                    Box(modifier = Modifier.weight(1f)) {
                        PaletteCard(
                            name = ft.displayName,
                            blurb = ft.family,
                            theme = ft.theme,
                            selected = project.theme.paletteName == ft.id,
                            onClick = { onThemeChange(project.copy(theme = ft.theme)) }
                        )
                    }
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun PaletteCard(
    name: String,
    blurb: String,
    theme: ThemeConfig,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) theme.primary.toComposeColor()
    else MaterialTheme.colorScheme.outlineVariant

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                listOf(
                    theme.background, theme.primary, theme.secondary,
                    theme.tertiary, theme.surface
                ).forEach { hex ->
                    Box(
                        modifier = Modifier.weight(1f).fillMaxHeight()
                            .background(hex.toComposeColor())
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(
                blurb, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1
            )
            if (selected) {
                Spacer(Modifier.height(4.dp))
                Text(
                    "● Active", style = MaterialTheme.typography.labelSmall,
                    color = theme.primary.toComposeColor(), fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
