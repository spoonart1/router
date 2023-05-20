package com.spoonart.router.utils

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spoonart.router.factory.ActivityRouterFactory
import com.spoonart.router.implementation.Router
import com.spoonart.router.implementation.RouterKey

/**
 * this is a function to navigate between activity
 * [router] this is mandatory and cannot be null
 * [routerKey] the key that binds the path of your scene (activity)
 * [bundle] (optional) use the bundle to pass the data between activity
 * [finishAfter] set true to finish the activity exactly once new activity launched
 * [flag] (Optional) use the Intent.Flag_xxx when launching new activity
 */
fun AppCompatActivity.navigate(
    //a router instance
    router: Router,

    //yaml scene id for activity / fragment
    routerKey: RouterKey,

    //bundle extra for data passing
    bundle: Bundle? = null,

    //when set to true, by default will finish the corresponding activity (caller),
    //unless you for custom RouterFactory
    finishAfter: Boolean = false,

    //a flag command for the next activity
    flag: Int? = null,
) {
    val routerFactory = object : ActivityRouterFactory(
        routerKey = routerKey,
        finishAfter = finishAfter,
        flag = flag
    ) {
        override val context: Context
            get() = applicationContext
    }
    routerFactory.bundle = bundle
    router.routeTo(routerFactory)
}
