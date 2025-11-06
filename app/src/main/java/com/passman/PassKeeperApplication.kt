package com.passman

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.support.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PassKeeperApplication : MultiDexApplication() {
    companion object{
        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}