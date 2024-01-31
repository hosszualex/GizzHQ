package com.ah.gizzhq

import android.app.Application
import com.ah.gizzhq.data.AppwriteService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GizzHQApp: Application() {
    override fun onCreate() {
        super.onCreate()
        System.setProperty("kotlinx.coroutines.debug", if(BuildConfig.DEBUG) "on" else "off")
    }
}