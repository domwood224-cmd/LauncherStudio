package com.napcity.launcherstudio

import android.content.pm.LauncherApps
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.napcity.launcherstudio.ui.LauncherScreen
import com.napcity.launcherstudio.ui.theme.LauncherStudioTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcherApps = getSystemService(LauncherApps::class.java)
        val appRepository = AppRepository(launcherApps, Process.myUserHandle())

        setContent {
            LauncherStudioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LauncherScreen(appRepository)
                }
            }
        }
    }

    override fun onBackPressed() {
        // Launcher handles back: close drawer/folders, don't exit
        // Compose BackHandler in LauncherScreen manages this
    }
}
