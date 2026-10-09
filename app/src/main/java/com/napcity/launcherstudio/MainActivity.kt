package com.napcity.launcherstudio

import android.content.pm.LauncherApps
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.napcity.launcherstudio.data.ProjectRepository
import com.napcity.launcherstudio.ui.LauncherScreen
import com.napcity.launcherstudio.ui.studio.StudioScreen
import com.napcity.launcherstudio.ui.theme.LauncherStudioTheme

class MainActivity : ComponentActivity() {

    private lateinit var appRepository: AppRepository
    private lateinit var projectRepository: ProjectRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcherApps = getSystemService(LauncherApps::class.java)
        appRepository = AppRepository(launcherApps, Process.myUserHandle())
        projectRepository = ProjectRepository(applicationContext)

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
                            onOpenStudio = { inStudio = true }
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
