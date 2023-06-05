package com.spoonart.routeractivity.application

import android.app.Application
import com.spoonart.painter.impl.Painter
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    @Inject
    lateinit var painter: Painter

    override fun onCreate() {
        super.onCreate()

        painter.install(this)
    }
}
