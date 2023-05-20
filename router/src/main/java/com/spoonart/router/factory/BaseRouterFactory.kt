package com.spoonart.router.factory

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.spoonart.router.implementation.RouterKey


abstract class BaseRouterFactory {
    var bundle: Bundle? = null

    abstract val routerKey: RouterKey

    abstract val context: Context

    abstract fun transmit(clazzName: String)
}

abstract class ActivityRouterFactory(
    override val routerKey: RouterKey,
    private val finishAfter: Boolean = false,
    private val flag: Int? = null,
) : BaseRouterFactory() {

    override fun transmit(clazzName: String) {
        try {
            val clazz = Class.forName(clazzName)
            val bundle = this.bundle
            val flag = this.flag
            val intent = Intent(context, clazz)

            if (flag != null)
                intent.flags = flag
            if (bundle != null)
                intent.putExtras(bundle)

            context.startActivity(intent)

            if (finishAfter) {
                (context as Activity).finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

abstract class FragmentRouterFactory(
    private val fragmentTag: String? = null,
    private val fragmentId: Int? = null,
) : BaseRouterFactory() {

    abstract fun fragmentHost(): Activity

    override fun transmit(clazzName: String) {
        println("direct to fragment host with id")
    }
}
