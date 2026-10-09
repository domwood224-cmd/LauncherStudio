package com.napcity.launcherstudio

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

class LockAdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
    }
}
