package com.spoonart.router.implementation

import com.spoonart.router.factory.BaseRouterFactory
import com.spoonart.router.utils.YamlReader
import javax.inject.Inject

/**
 * define the key of each scene by using this interface,
 * can use enum  or object class that contains this value
 */
interface RouterKey {
    val id: String

    companion object {
        fun CREATE(keyId: String) = object : RouterKey {
            override val id: String
                get() = keyId

        }
    }
}
/**
 * an interface that exposes the function to navigate to desired scene
 */
interface Router {
    fun routeTo(baseRouterFactory: BaseRouterFactory)
}

/**
 * Implement the router with the given property in constructor
 * you can provide this class as Singleton with Hilt/Dagger injection method
 */
class RouterImpl @Inject constructor(
    yamlReader: YamlReader
) : Router {

    private val routes = yamlReader.routes

    /**
     * @baseRouterFactory is an object that control the navigation
     */
    override fun routeTo(baseRouterFactory: BaseRouterFactory) {
        val clazz = validateEntry(baseRouterFactory.routerKey)
        baseRouterFactory.transmit(clazz)
    }

    /**
     * returns the value(path) of the activities/fragment's key that is written in yaml file
     * an exception will thrown when the key cannot be found
     */
    private fun validateEntry(key: RouterKey): String {
        val res = routes.find { it.key == key.id } ?: throw NullPointerException(
            "Cannot find the given key with name $key in the .yaml file, " +
                    "make sure you register the key correctly"
        )
        return res.path
    }
}
