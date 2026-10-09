package com.napcity.launcherstudio.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.napcity.launcherstudio.AppInfo
import com.napcity.launcherstudio.AppRepository
import com.napcity.launcherstudio.data.DockStyle
import com.napcity.launcherstudio.data.GestureAction
import com.napcity.launcherstudio.data.IconShape
import com.napcity.launcherstudio.data.LauncherProject
import com.napcity.launcherstudio.widgets.WidgetManager
import com.napcity.launcherstudio.widgets.WidgetStrip

@Composable
fun LauncherScreen(
    repository: AppRepository,
    project: LauncherProject?,
    widgetManager: WidgetManager,
    onOpenStudio: () -> Unit,
    onPickWidget: () -> Unit,
    onGesture: (GestureAction) -> Unit
) {
    val apps by repository.apps.collectAsState()
    var drawerOpen by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    BackHandler(enabled = drawerOpen) {
        drawerOpen = false
    }

    val columns = project?.layout?.gridColumns ?: 4
    val iconSize = project?.layout?.iconSizeDp ?: 56
    val showLabels = project?.layout?.showLabels ?: true
    val iconShape = project?.icons?.shape ?: IconShape.ROUNDED
    val gestures = project?.gestures

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(gestures) {
                detectTapGestures(
                    onDoubleTap = {
                        when (gestures?.doubleTapAction) {
                            GestureAction.LOCK_SCREEN,
                            GestureAction.OPEN_STUDIO -> onGesture(gestures.doubleTapAction)
                            GestureAction.OPEN_DRAWER -> drawerOpen = true
                            else -> {}
                        }
                    }
                )
            }
    ) {
        // Wallpaper background
        WallpaperBackground(project = project)

        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar — studio shortcut
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 48.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onOpenStudio) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Open Studio",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Widgets
            WidgetStrip(manager = widgetManager, onPickWidget = onPickWidget)
            Spacer(Modifier.height(8.dp))

            // Home grid — driven by project layout
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(apps.take(16)) { app ->
                    AppIcon(
                        app = app,
                        iconSizeDp = iconSize,
                        showLabel = showLabels,
                        shape = iconShape,
                        cornerRadius = project?.icons?.cornerRadius ?: 28,
                        onClick = { repository.launch(app) }
                    )
                }
            }

            // Dock — driven by project dock config
            if (project?.dock?.enabled != false) {
                val forgeVariant = project?.dock?.forgeVariant ?: ""
                if (forgeVariant.isNotEmpty() && project != null) {
                    com.napcity.launcherstudio.ui.forge.ForgeDock(
                        variant = forgeVariant,
                        theme = project.theme,
                        onOpen = { drawerOpen = true }
                    )
                } else {
                    DockBar(
                        style = project?.dock?.style ?: DockStyle.BAR,
                        onDrawerOpen = { drawerOpen = true }
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth().height(24.dp)
                        .clickable { drawerOpen = true }
                )
            }
        }

        if (drawerOpen) {
            AppDrawer(
                apps = apps,
                project = project,
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it },
                onAppClick = {
                    repository.launch(it)
                    drawerOpen = false
                },
                onDismiss = { drawerOpen = false }
            )
        }
    }
}

@Composable
fun WallpaperBackground(project: LauncherProject?) {
    val context = LocalContext.current
    val wallpaperId = project?.wallpaper?.wallpaperId ?: "none"
    val dim = (project?.wallpaper?.dimPercent ?: 20) / 100f

    if (wallpaperId != "none") {
        val resId = context.resources.getIdentifier(wallpaperId, "drawable", context.packageName)
        if (resId != 0) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(resId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Dim overlay
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Black.copy(alpha = dim))
                )
            }
        }
    }
}

@Composable
fun AppIcon(
    app: AppInfo,
    iconSizeDp: Int = 56,
    showLabel: Boolean = true,
    shape: IconShape = IconShape.ROUNDED,
    cornerRadius: Int = 28,
    onClick: () -> Unit
) {
    val clipShape = when (shape) {
        IconShape.CIRCLE -> CircleShape
        IconShape.ROUNDED -> RoundedCornerShape(cornerRadius.coerceIn(0, 50).toFloat())
        IconShape.SQUIRCLE -> RoundedCornerShape(38f)
        IconShape.TEARDROP -> RoundedCornerShape(
            topStart = 50f, topEnd = 50f, bottomStart = 50f, bottomEnd = 8f
        )
        IconShape.SYSTEM -> RoundedCornerShape(0f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick).padding(4.dp)
    ) {
        if (shape == IconShape.SYSTEM) {
            Image(
                bitmap = app.icon.toBitmap(144, 144).asImageBitmap(),
                contentDescription = app.label,
                modifier = Modifier.size(iconSizeDp.dp)
            )
        } else {
            Image(
                bitmap = app.icon.toBitmap(144, 144).asImageBitmap(),
                contentDescription = app.label,
                modifier = Modifier.size(iconSizeDp.dp).clip(clipShape)
            )
        }
        if (showLabel) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = app.label,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Composable
fun DockBar(
    style: DockStyle,
    onDrawerOpen: () -> Unit
) {
    val modifier = when (style) {
        DockStyle.FLOATING -> Modifier.fillMaxWidth().padding(16.dp)
        else -> Modifier.fillMaxWidth()
    }
    Surface(
        modifier = modifier.height(72.dp).clickable(onClick = onDrawerOpen),
        tonalElevation = if (style == DockStyle.FLOATING) 8.dp else 2.dp,
        shape = if (style == DockStyle.FLOATING) RoundedCornerShape(24.dp)
        else RoundedCornerShape(0.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = "▲ Swipe up for apps",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun AppDrawer(
    apps: List<AppInfo>,
    project: LauncherProject?,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAppClick: (AppInfo) -> Unit,
    onDismiss: () -> Unit
) {
    val filtered = if (searchQuery.isBlank()) apps
    else apps.filter { it.label.contains(searchQuery, ignoreCase = true) }
    val columns = project?.drawer?.gridColumns ?: 4
    val showSearch = project?.drawer?.showSearch ?: true

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(48.dp))

            if (showSearch) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search apps") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    singleLine = true
                )
            } else {
                Spacer(Modifier.height(16.dp))
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filtered) { app ->
                    AppIcon(
                        app = app,
                        iconSizeDp = project?.layout?.iconSizeDp ?: 56,
                        showLabel = project?.layout?.showLabels ?: true,
                        shape = project?.icons?.shape ?: IconShape.ROUNDED,
                        cornerRadius = project?.icons?.cornerRadius ?: 28,
                        onClick = { onAppClick(app) }
                    )
                }
            }

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            ) {
                Text("Close")
            }
        }
    }
}
