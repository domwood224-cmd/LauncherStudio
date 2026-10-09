package com.napcity.launcherstudio.widgets

import android.app.Activity
import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

const val WIDGET_HOST_ID = 1024
const val REQUEST_PICK_WIDGET = 1001
const val REQUEST_CREATE_WIDGET = 1002

/**
 * Manages Android app widgets on the launcher home screen.
 * Persists widget ids in SharedPreferences so they survive restarts.
 */
class WidgetManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("launcher_widgets", Context.MODE_PRIVATE)
    val appWidgetHost = AppWidgetHost(context, WIDGET_HOST_ID)
    val appWidgetManager: AppWidgetManager =
        context.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager

    fun startListening() {
        try { appWidgetHost.startListening() } catch (_: Exception) {}
    }

    fun stopListening() {
        try { appWidgetHost.stopListening() } catch (_: Exception) {}
    }

    fun widgetIds(): List<Int> =
        prefs.getStringSet("ids", emptySet())?.mapNotNull { it.toIntOrNull() } ?: emptyList()

    fun allocateId(): Int = appWidgetHost.allocateAppWidgetId()

    fun addWidget(id: Int) {
        val ids = widgetIds().toMutableSet()
        ids.add(id)
        prefs.edit().putStringSet("ids", ids.map { it.toString() }.toSet()).apply()
    }

    fun removeWidget(id: Int) {
        try { appWidgetHost.deleteAppWidgetId(id) } catch (_: Exception) {}
        val ids = widgetIds().toMutableSet()
        ids.remove(id)
        prefs.edit().putStringSet("ids", ids.map { it.toString() }.toSet()).apply()
    }
}

@Composable
fun rememberWidgetManager(): WidgetManager {
    val context = LocalContext.current
    val manager = remember { WidgetManager(context.applicationContext) }
    DisposableEffect(Unit) {
        manager.startListening()
        onDispose { manager.stopListening() }
    }
    return manager
}

/**
 * Home-screen widget strip — shows placed widgets + add button.
 */
@Composable
fun WidgetStrip(
    manager: WidgetManager,
    onPickWidget: () -> Unit
) {
    val context = LocalContext.current
    var widgetIds by remember { mutableStateOf(manager.widgetIds()) }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        widgetIds.forEach { id ->
            val info: AppWidgetProviderInfo? = manager.appWidgetManager.getAppWidgetInfo(id)
            if (info != null) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AndroidView(
                        factory = { ctx ->
                            manager.appWidgetHost.createView(ctx, id, info).apply {
                                setAppWidget(id, info)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp)
                    )
                    IconButton(
                        onClick = {
                            manager.removeWidget(id)
                            widgetIds = manager.widgetIds()
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Default.Close, "Remove widget",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        // Add widget button
        OutlinedButton(
            onClick = onPickWidget,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, null, Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Add widget")
        }
    }

    // Refresh when returning from picker
    LaunchedEffect(Unit) {
        widgetIds = manager.widgetIds()
    }
}
