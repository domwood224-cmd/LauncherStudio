package com.napcity.launcherstudio.ui.studio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.napcity.launcherstudio.data.DockStyle
import com.napcity.launcherstudio.data.DrawerStyle
import com.napcity.launcherstudio.data.HomeStyle
import com.napcity.launcherstudio.data.LauncherProject

/**
 * Layout editor — home grid, drawer style, dock options.
 * All changes apply live to the editing project.
 */
@Composable
fun LayoutEditorTab(
    project: LauncherProject,
    onProjectChange: (LauncherProject) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // ---- Home screen ----
        SectionTitle("Home Screen")
        OptionRow("Layout style", HomeStyle.values().map { it.name }) { selected ->
            onProjectChange(project.copy(
                layout = project.layout.copy(style = HomeStyle.valueOf(selected))
            ))
        }
        SliderRow(
            label = "Grid columns",
            value = project.layout.gridColumns.toFloat(),
            range = 3f..6f,
            display = "${project.layout.gridColumns}"
        ) {
            onProjectChange(project.copy(
                layout = project.layout.copy(gridColumns = it.toInt())
            ))
        }
        SliderRow(
            label = "Icon size",
            value = project.layout.iconSizeDp.toFloat(),
            range = 40f..72f,
            display = "${project.layout.iconSizeDp}dp"
        ) {
            onProjectChange(project.copy(
                layout = project.layout.copy(iconSizeDp = it.toInt())
            ))
        }
        SwitchRow("Show app labels", project.layout.showLabels) {
            onProjectChange(project.copy(layout = project.layout.copy(showLabels = it)))
        }

        Divider()

        // ---- App drawer ----
        SectionTitle("App Drawer")
        OptionRow("Drawer style", DrawerStyle.values().map { it.name }) { selected ->
            onProjectChange(project.copy(
                drawer = project.drawer.copy(style = DrawerStyle.valueOf(selected))
            ))
        }
        SwitchRow("Show search bar", project.drawer.showSearch) {
            onProjectChange(project.copy(drawer = project.drawer.copy(showSearch = it)))
        }

        Divider()

        // ---- Dock ----
        SectionTitle("Dock")
        SwitchRow("Enable dock", project.dock.enabled) {
            onProjectChange(project.copy(dock = project.dock.copy(enabled = it)))
        }
        if (project.dock.enabled) {
            OptionRow("Dock style", DockStyle.values().map { it.name }) { selected ->
                onProjectChange(project.copy(
                    dock = project.dock.copy(style = DockStyle.valueOf(selected), forgeVariant = "")
                ))
            }
            Text(
                "ForgeUI exclusive docks",
                style = MaterialTheme.typography.bodyLarge
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "" to "Standard",
                    "floating-glass" to "Floating Glass",
                    "aurora-pill" to "Aurora Pill",
                    "neon-edge" to "Neon Edge"
                ).forEach { (id, label) ->
                    FilterChip(
                        selected = project.dock.forgeVariant == id,
                        onClick = {
                            onProjectChange(project.copy(
                                dock = project.dock.copy(forgeVariant = id)
                            ))
                        },
                        label = { Text(label) }
                    )
                }
            }
            SliderRow(
                label = "Dock slots",
                value = project.dock.slots.toFloat(),
                range = 3f..7f,
                display = "${project.dock.slots}"
            ) {
                onProjectChange(project.copy(dock = project.dock.copy(slots = it.toInt())))
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun SliderRow(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    display: String,
    onChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            Text(display, style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary)
        }
        Slider(
            value = value,
            onValueChange = onChange,
            valueRange = range,
            steps = (range.endInclusive - range.start).toInt() - 1
        )
    }
}

@Composable
private fun SwitchRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onChange)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OptionRow(
    label: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    // Show as segmented buttons for 3 or fewer, dropdown for more
    Column {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        if (options.size <= 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEach { opt ->
                    FilterChip(
                        selected = false,
                        onClick = { onSelect(opt) },
                        label = { Text(opt) }
                    )
                }
            }
            Text(
                "Tap to apply — live preview updates",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                OutlinedTextField(
                    value = options.first(),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach { opt ->
                        DropdownMenuItem(
                            text = { Text(opt) },
                            onClick = { onSelect(opt); expanded = false }
                        )
                    }
                }
            }
        }
    }
}
