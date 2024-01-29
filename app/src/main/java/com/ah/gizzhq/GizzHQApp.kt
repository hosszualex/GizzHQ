package com.ah.gizzhq

import android.app.Application
import com.ah.gizzhq.data.Appwrite
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GizzHQApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Appwrite.init(this)
    }
}