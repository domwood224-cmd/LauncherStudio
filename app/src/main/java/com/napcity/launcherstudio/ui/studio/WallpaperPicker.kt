package com.napcity.launcherstudio.ui.studio

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.napcity.launcherstudio.data.BuiltInWallpapers
import com.napcity.launcherstudio.data.LauncherProject
import com.napcity.launcherstudio.ui.theme.toComposeColor

/**
 * Wallpaper picker — grid of built-in wallpapers + dim slider.
 */
@Composable
fun WallpaperPickerTab(
    project: LauncherProject,
    onProjectChange: (LauncherProject) -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(BuiltInWallpapers.all) { wp ->
                val selected = project.wallpaper.wallpaperId == wp.id
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(9f / 16f)
                        .border(
                            width = if (selected) 3.dp else 1.dp,
                            color = if (selected) project.theme.primary.toComposeColor()
                            else MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            onProjectChange(project.copy(
                                wallpaper = project.wallpaper.copy(wallpaperId = wp.id)
                            ))
                        },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (wp.id != "none") {
                            val resId = context.resources.getIdentifier(
                                wp.id, "drawable", context.packageName
                            )
                            if (resId != 0) {
                                Image(
                                    painter = painterResource(resId),
                                    contentDescription = wp.displayName,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No wallpaper",
                                    style = MaterialTheme.typography.labelMedium)
                            }
                        }
                        // Label at bottom
                        Surface(
                            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                        ) {
                            Text(
                                wp.displayName,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Dim slider
        if (project.wallpaper.wallpaperId != "none") {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Dim for readability", style = MaterialTheme.typography.bodyLarge)
                    Text("${project.wallpaper.dimPercent}%",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary)
                }
                Slider(
                    value = project.wallpaper.dimPercent.toFloat(),
                    onValueChange = {
                        onProjectChange(project.copy(
                            wallpaper = project.wallpaper.copy(dimPercent = it.toInt())
                        ))
                    },
                    valueRange = 0f..80f
                )
            }
        }
    }
}
