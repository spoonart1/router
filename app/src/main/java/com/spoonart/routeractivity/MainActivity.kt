package com.spoonart.routeractivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.spoonart.router.base.BaseRouterActivity
import com.spoonart.router.implementation.Router
import com.spoonart.router.utils.navigate
import com.spoonart.routeractivity.constant.RoutePaths
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseRouterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn_nav)
        btn.setOnClickListener {
            navigate(
                router = router,
                routerKey = RoutePaths.SpoonArtScene.routerKey,
                flag = Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }
    }
}
