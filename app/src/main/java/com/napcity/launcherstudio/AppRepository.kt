package com.napcity.launcherstudio

import android.content.pm.LauncherApps
import android.graphics.drawable.Drawable
import android.os.UserHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppInfo(
    val packageName: String,
    val activityName: String,
    val label: String,
    val icon: Drawable,
    val userHandle: UserHandle
)

class AppRepository(
    private val launcherApps: LauncherApps,
    private val userHandle: UserHandle
) {
    private val _apps = MutableStateFlow<List<AppInfo>>(emptyList())
    val apps: StateFlow<List<AppInfo>> = _apps.asStateFlow()

    private val callback = object : LauncherApps.Callback() {
        override fun onPackageAdded(packageName: String, user: UserHandle) = refresh()
        override fun onPackageChanged(packageName: String, user: UserHandle) = refresh()
        override fun onPackageRemoved(packageName: String, user: UserHandle) = refresh()
        override fun onPackagesAvailable(packages: Array<String>, user: UserHandle, replacing: Boolean) = refresh()
        override fun onPackagesUnavailable(packages: Array<String>, user: UserHandle, replacing: Boolean) = refresh()
    }

    init {
        launcherApps.registerCallback(callback)
        refresh()
    }

    fun refresh() {
        val list = launcherApps.getActivityList(null, userHandle).map { info ->
            AppInfo(
                packageName = info.componentName.packageName,
                activityName = info.componentName.className,
                label = info.label.toString(),
                icon = info.getBadgedIcon(0),
                userHandle = userHandle
            )
        }.sortedBy { it.label.lowercase() }
        _apps.value = list
    }

    fun launch(app: AppInfo) {
        val component = android.content.ComponentName(app.packageName, app.activityName)
        launcherApps.startMainActivity(component, app.userHandle, null, null)
    }

    fun onDestroy() {
        launcherApps.unregisterCallback(callback)
    }
}
