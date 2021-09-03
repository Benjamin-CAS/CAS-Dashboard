package com.cas.casdashboard

import android.app.Application
import com.tencent.mmkv.MMKV

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
    companion object{
        const val TAG = "Application"
    }
}