package com.spoonart.routeractivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.spoonart.router.base.BaseRouterActivity
import com.spoonart.router.factory.ActivityRouterFactory
import com.spoonart.router.utils.navigate
import com.spoonart.routeractivity.constant.RoutePaths
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseRouterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn_nav)

        val routerFactory = object : ActivityRouterFactory(
            routerKey = RoutePaths.SpoonArtScene.routerKey,
            finishAfter = false,
            flag = Intent.FLAG_ACTIVITY_NEW_TASK
        ) {
            override val context: Context
                get() = applicationContext
        }


        btn.setOnClickListener {
//            navigate(
//                router = router,
//                routerKey = RoutePaths.SpoonArtScene.routerKey,
//                flag = Intent.FLAG_ACTIVITY_NEW_TASK
//            )

            showAlertDialog()
            navigate(
                router = router,
                routerFactory = routerFactory
            )
        }
    }
}


fun Activity.showAlertDialog(){
    AlertDialog.Builder(this)
        .setTitle("Percobaan")
        .setNeutralButton("OK", null)
        .show()
}
