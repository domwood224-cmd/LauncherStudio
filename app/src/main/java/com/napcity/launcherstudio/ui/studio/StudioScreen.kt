package com.napcity.launcherstudio.ui.studio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.napcity.launcherstudio.data.LauncherProject
import com.napcity.launcherstudio.data.ProjectRepository
import kotlinx.coroutines.launch

/**
 * The Studio — Dom's launcher maker.
 * Tabs: Projects | Theme | Layout | Preview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioScreen(
    repository: ProjectRepository,
    onLaunchPreview: () -> Unit,
    onExitStudio: () -> Unit
) {
    val projects by repository.projects.collectAsState(initial = emptyList())
    val activeProject by repository.activeProject.collectAsState(initial = null)
    var selectedTab by remember { mutableIntStateOf(0) }
    var editingProject by remember { mutableStateOf<LauncherProject?>(null) }
    val scope = rememberCoroutineScope()

    // Editing project mirrors the active one until user picks a different project to edit
    LaunchedEffect(activeProject) {
        if (editingProject == null) editingProject = activeProject
    }

    val tabs = listOf("Projects", "Theme", "Layout", "Preview")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Launcher Studio", fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = onExitStudio) { Text("Done") }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val icons = listOf(
                    Icons.Default.Folder to "Projects",
                    Icons.Default.Palette to "Theme",
                    Icons.Default.Dashboard to "Layout",
                    Icons.Default.Visibility to "Preview"
                )
                icons.forEachIndexed { i, (icon, label) ->
                    NavigationBarItem(
                        selected = selectedTab == i,
                        onClick = { selectedTab = i },
                        icon = { Icon(icon, label) },
                        label = { Text(label) }
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        val new = repository.createProject("My Launcher ${projects.size + 1}")
                        editingProject = new
                        selectedTab = 1
                    }
                }) {
                    Icon(Icons.Default.Add, "New project")
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (selectedTab) {
                0 -> ProjectListTab(
                    projects = projects,
                    activeId = activeProject?.id,
                    onSelect = { editingProject = it; selectedTab = 1 },
                    onActivate = { scope.launch { repository.setActiveProject(it.id) } },
                    onDelete = { scope.launch { repository.deleteProject(it.id) } }
                )
                1 -> editingProject?.let {
                    ThemePickerTab(project = it) { updated ->
                        editingProject = updated
                        scope.launch { repository.saveProject(updated) }
                    }
                }
                2 -> editingProject?.let {
                    LayoutEditorTab(project = it) { updated ->
                        editingProject = updated
                        scope.launch { repository.saveProject(updated) }
                    }
                }
                3 -> editingProject?.let {
                    StudioPreviewTab(project = it, onLaunchPreview = onLaunchPreview)
                }
            }
        }
    }
}

@Composable
private fun ProjectListTab(
    projects: List<LauncherProject>,
    activeId: String?,
    onSelect: (LauncherProject) -> Unit,
    onActivate: (LauncherProject) -> Unit,
    onDelete: (LauncherProject) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(projects, key = { it.id }) { project ->
            val isActive = project.id == activeId
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onSelect(project) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(project.name, fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall)
                        Text(project.theme.paletteName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    if (isActive) {
                        AssistChip(
                            onClick = {},
                            label = { Text("Active") },
                            leadingIcon = { Icon(Icons.Default.Check, null, Modifier.size(16.dp)) }
                        )
                    } else {
                        TextButton(onClick = { onActivate(project) }) { Text("Use") }
                    }
                    IconButton(onClick = { onDelete(project) }) {
                        Icon(Icons.Default.Delete, "Delete",
                            tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Composable
private fun StudioPreviewTab(
    project: LauncherProject,
    onLaunchPreview: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            project.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "${project.theme.paletteName} · ${project.layout.style} · ${project.drawer.style}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onLaunchPreview, modifier = Modifier.fillMaxWidth()) {
            Text("Preview as Launcher")
        }
        Spacer(Modifier.height(8.dp))
        Text(
            "Activates this project and shows the live launcher",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
