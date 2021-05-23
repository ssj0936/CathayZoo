package com.timothy.zoo

import android.app.Application
import android.content.Context
import timber.log.Timber

class MainApp:Application() {
    companion object{
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        //timber settings
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appContext = applicationContext
    }
}