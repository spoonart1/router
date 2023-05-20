package com.spoonart.router.model

/**
 * [key] and [path]
 * that written in yaml will be parsed into this class model
 */
internal open class Route(
    val key: String,
    val path: String,
)

/**
 * This class model intended for Activity Scene in yaml
 * with key: [activities]
 */
internal class ActivityRoute(
    key: String,
    path: String,
) : Route(key, path)

/**
 * This class model intended for Activity Scene in yaml
 * with key: [fragments]
 */
internal class FragmentRoute(
    key: String,
    path: String,
) : Route(key, path)
