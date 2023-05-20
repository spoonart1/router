package com.spoonart.routeractivity.constant

import com.spoonart.router.implementation.RouterKey

enum class RoutePaths(
    val routerKey: RouterKey,
) {
    MainScene(RouterKey.CREATE("main-activity")),
    SpoonArtScene(RouterKey.CREATE("spoonart-activity"))
}
