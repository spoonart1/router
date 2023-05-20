package com.spoonart.routeractivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.spoonart.router.base.BaseRouterActivity
import com.spoonart.router.utils.navigate
import com.spoonart.routeractivity.constant.RoutePaths
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpoonartActivity : BaseRouterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spoonart)

        val btn = findViewById<Button>(R.id.btn_nav)
        btn.setOnClickListener {
            navigate(
                router = router,
                routerKey = RoutePaths.MainScene.routerKey,
                flag = Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }
    }
}
