package com.spoonart.painter.impl

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.Toast
import com.spoonart.painter.core.MethodReader
import com.spoonart.painter.core.MethodReaderImpl
import javax.inject.Inject


interface Painter {
    fun install(application: Application)
}

class PainterImpl @Inject constructor(
    private val methodReader: MethodReader = MethodReaderImpl(),
) : Painter {

    private val appLifeCycleCallback by lazy {
        object : PainterLifeCycleCallback() {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                methodReader.getCurrentMethodName(activity.packageName) { message ->
                    activity.runOnUiThread {
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun install(application: Application) {
        application.registerActivityLifecycleCallbacks(appLifeCycleCallback)
    }
}

internal open class PainterLifeCycleCallback : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

}
