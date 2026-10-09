package com.napcity.launcherstudio.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.napcity.launcherstudio.AppInfo
import com.napcity.launcherstudio.AppRepository

@Composable
fun LauncherScreen(repository: AppRepository) {
    val apps by repository.apps.collectAsState()
    var drawerOpen by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    BackHandler(enabled = drawerOpen) {
        drawerOpen = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Home screen — favorites grid (first 12 apps for now)
        HomeGrid(
            apps = apps.take(16),
            onAppClick = { repository.launch(it) },
            onDrawerOpen = { drawerOpen = true }
        )

        // App drawer overlay
        if (drawerOpen) {
            AppDrawer(
                apps = apps,
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
fun HomeGrid(
    apps: List<AppInfo>,
    onAppClick: (AppInfo) -> Unit,
    onDrawerOpen: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Spacer for status bar
        Spacer(modifier = Modifier.height(48.dp))

        // App grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(apps) { app ->
                AppIcon(app = app, onClick = { onAppClick(app) })
            }
        }

        // Dock — swipe up to open drawer
        DockBar(onDrawerOpen = onDrawerOpen)
    }
}

@Composable
fun AppIcon(app: AppInfo, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick).padding(4.dp)
    ) {
        Image(
            bitmap = app.icon.toBitmap(144, 144).asImageBitmap(),
            contentDescription = app.label,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = app.label,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DockBar(onDrawerOpen: () -> Unit) {
    // Swipe up gesture area — for now, a button
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable(onClick = onDrawerOpen),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "▲ Swipe up for apps",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun AppDrawer(
    apps: List<AppInfo>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAppClick: (AppInfo) -> Unit,
    onDismiss: () -> Unit
) {
    val filtered = if (searchQuery.isBlank()) apps
    else apps.filter { it.label.contains(searchQuery, ignoreCase = true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(48.dp))

            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Search apps") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                singleLine = true
            )

            // App list
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filtered) { app ->
                    AppIcon(app = app, onClick = { onAppClick(app) })
                }
            }

            // Close button
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            ) {
                Text("Close")
            }
        }
    }
}
