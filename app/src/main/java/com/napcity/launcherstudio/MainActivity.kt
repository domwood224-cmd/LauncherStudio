package com.napcity.launcherstudio

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.napcity.launcherstudio.data.GestureAction
import com.napcity.launcherstudio.data.ProjectRepository
import com.napcity.launcherstudio.ui.LauncherScreen
import com.napcity.launcherstudio.ui.studio.StudioScreen
import com.napcity.launcherstudio.ui.theme.LauncherStudioTheme
import com.napcity.launcherstudio.widgets.REQUEST_CREATE_WIDGET
import com.napcity.launcherstudio.widgets.REQUEST_PICK_WIDGET
import com.napcity.launcherstudio.widgets.WidgetManager

class MainActivity : ComponentActivity() {

    private lateinit var appRepository: AppRepository
    private lateinit var projectRepository: ProjectRepository
    private lateinit var widgetManager: WidgetManager

    /** Pending widget id being configured */
    private var pendingWidgetId: Int = -1

    private val pickWidgetLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            val extras = data.extras ?: return@registerForActivityResult
            val appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
            if (appWidgetId == -1) return@registerForActivityResult
            pendingWidgetId = appWidgetId
            val info = widgetManager.appWidgetManager.getAppWidgetInfo(appWidgetId)
            if (info.configure != null) {
                // Needs configuration
                val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE).apply {
                    component = info.configure
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                }
                configureWidgetLauncher.launch(intent)
            } else {
                widgetManager.addWidget(appWidgetId)
            }
        }
    }

    private val configureWidgetLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && pendingWidgetId != -1) {
            widgetManager.addWidget(pendingWidgetId)
        } else if (pendingWidgetId != -1) {
            widgetManager.appWidgetHost.deleteAppWidgetId(pendingWidgetId)
        }
        pendingWidgetId = -1
    }

    fun launchWidgetPicker() {
        val appWidgetId = widgetManager.allocateId()
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_PICK).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_EXTRAS, Bundle())
        }
        pickWidgetLauncher.launch(intent)
    }

    fun lockScreen() {
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val admin = ComponentName(this, LockAdminReceiver::class.java)
        if (dpm.isAdminActive(admin)) {
            dpm.lockNow()
        } else {
            // Request device admin
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin)
                putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Launcher Studio needs device admin to lock the screen on double-tap.")
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcherApps = getSystemService(LauncherApps::class.java)
        appRepository = AppRepository(launcherApps, Process.myUserHandle())
        projectRepository = ProjectRepository(applicationContext)
        widgetManager = WidgetManager(applicationContext)

        setContent {
            val activeProject by projectRepository.activeProject.collectAsState(initial = null)
            var inStudio by remember { mutableStateOf(false) }

            LauncherStudioTheme(theme = activeProject?.theme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (inStudio) {
                        StudioScreen(
                            repository = projectRepository,
                            onLaunchPreview = { inStudio = false },
                            onExitStudio = { inStudio = false }
                        )
                    } else {
                        LauncherScreen(
                            repository = appRepository,
                            project = activeProject,
                            widgetManager = widgetManager,
                            onOpenStudio = { inStudio = true },
                            onPickWidget = { launchWidgetPicker() },
                            onGesture = { action ->
                                when (action) {
                                    GestureAction.LOCK_SCREEN -> lockScreen()
                                    GestureAction.OPEN_STUDIO -> inStudio = true
                                    else -> {}
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        // Launcher handles back via Compose BackHandler
    }

    override fun onDestroy() {
        appRepository.onDestroy()
        super.onDestroy()
    }
}
