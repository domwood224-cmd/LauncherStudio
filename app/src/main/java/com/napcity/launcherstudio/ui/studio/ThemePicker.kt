package com.napcity.launcherstudio.ui.studio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.napcity.launcherstudio.data.BuiltInPalettes
import com.napcity.launcherstudio.data.LauncherProject
import com.napcity.launcherstudio.ui.theme.toComposeColor

/**
 * Theme picker — grid of palettes from Dom's design library.
 * Tapping a palette applies it to the editing project immediately.
 */
@Composable
fun ThemePickerTab(
    project: LauncherProject,
    onThemeChange: (LauncherProject) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(BuiltInPalettes.all) { palette ->
            val selected = project.theme.paletteName == palette.id
            PaletteCard(
                palette = palette,
                selected = selected,
                onClick = {
                    onThemeChange(project.copy(theme = palette.theme))
                }
            )
        }
    }
}

@Composable
private fun PaletteCard(
    palette: BuiltInPalettes.Palette,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) palette.theme.primary.toComposeColor()
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
            // Color strip preview
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                val colors = listOf(
                    palette.theme.background,
                    palette.theme.primary,
                    palette.theme.secondary,
                    palette.theme.tertiary,
                    palette.theme.surface
                )
                colors.forEach { hex ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(hex.toComposeColor())
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                palette.displayName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                palette.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
            if (selected) {
                Spacer(Modifier.height(4.dp))
                Text(
                    "● Active",
                    style = MaterialTheme.typography.labelSmall,
                    color = palette.theme.primary.toComposeColor(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
