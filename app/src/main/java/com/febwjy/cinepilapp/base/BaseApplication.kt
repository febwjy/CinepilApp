package com.febwjy.cinepilapp.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Febby Wijaya on 16/09/23.
 */
@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}