package com.febwjy.cinepilapp.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Febby Wijaya on 19/05/22.
 */
@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}