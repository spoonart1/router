package com.spoonart.router.base

import androidx.appcompat.app.AppCompatActivity
import com.spoonart.router.implementation.Router
import javax.inject.Inject

/**
 * Recommended to use this base activity class for each activity that registered in the AndroidManifest.xml
 * Note: must provide the router in your AppModule (SingletonComponent) by using dagger / hilt.
 */
abstract class BaseRouterActivity : AppCompatActivity() {
    @Inject
    lateinit var router: Router
}
