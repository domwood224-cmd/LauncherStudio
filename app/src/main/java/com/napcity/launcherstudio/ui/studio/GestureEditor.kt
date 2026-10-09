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
import com.napcity.launcherstudio.data.GestureAction
import com.napcity.launcherstudio.data.IconShape
import com.napcity.launcherstudio.data.LauncherProject

/**
 * Gestures + icon shape editor.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestureEditorTab(
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
        Text(
            "Gestures",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        GestureRow(
            label = "Double-tap",
            current = project.gestures.doubleTapAction,
            onSelect = {
                onProjectChange(project.copy(
                    gestures = project.gestures.copy(doubleTapAction = it)
                ))
            }
        )
        GestureRow(
            label = "Swipe down",
            current = project.gestures.swipeDownAction,
            onSelect = {
                onProjectChange(project.copy(
                    gestures = project.gestures.copy(swipeDownAction = it)
                ))
            }
        )
        GestureRow(
            label = "Swipe up",
            current = project.gestures.swipeUpAction,
            onSelect = {
                onProjectChange(project.copy(
                    gestures = project.gestures.copy(swipeUpAction = it)
                ))
            }
        )

        Text(
            "Double-tap → Lock needs device admin. You'll get a prompt the first time.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Divider()

        Text(
            "Icon Shape",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        IconShapeRow(
            current = project.icons.shape,
            onSelect = {
                onProjectChange(project.copy(icons = project.icons.copy(shape = it)))
            }
        )

        if (project.icons.shape == IconShape.ROUNDED) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Corner radius", style = MaterialTheme.typography.bodyLarge)
                Text("${project.icons.cornerRadius}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary)
            }
            Slider(
                value = project.icons.cornerRadius.toFloat(),
                onValueChange = {
                    onProjectChange(project.copy(
                        icons = project.icons.copy(cornerRadius = it.toInt())
                    ))
                },
                valueRange = 0f..50f
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GestureRow(
    label: String,
    current: GestureAction,
    onSelect: (GestureAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(
                value = current.name.replace("_", " "),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().width(180.dp)
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                GestureAction.values().forEach { action ->
                    DropdownMenuItem(
                        text = { Text(action.name.replace("_", " ")) },
                        onClick = { onSelect(action); expanded = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun IconShapeRow(
    current: IconShape,
    onSelect: (IconShape) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconShape.values().forEach { shape ->
            FilterChip(
                selected = current == shape,
                onClick = { onSelect(shape) },
                label = { Text(shape.name) }
            )
        }
    }
}
