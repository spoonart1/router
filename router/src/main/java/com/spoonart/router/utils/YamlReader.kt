package com.spoonart.router.utils

import android.content.res.AssetManager
import com.spoonart.router.model.ActivityRoute
import com.spoonart.router.model.FragmentRoute
import com.spoonart.router.model.Route
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException


class YamlReader(
    private val assetManager: AssetManager,
    private val fileName: String,
) {
    /**
     * lazy initialization will
     * initialize only when this property is called
     * this property should be only single-component for the entire application lifetime
     */
    internal val routes by lazy {
        assetManager.parse(fileName)
    }

    private fun readYamlFile(assetManager: AssetManager, fileName: String): Map<String, Any>? {
        return try {
            val inputStream = assetManager.open(fileName)
            val yaml = Yaml()
            val data = yaml.load<Map<String, Any>>(inputStream)
            inputStream.close()
            data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun AssetManager.parse(fileName: String): List<Route> {
        val data = readYamlFile(this, fileName)
        val routes = mutableListOf<Route>()
        if (data != null) {
            val activities = data["activities"] as? Map<String, String>
            validateYaml(activities ?: mapOf())
            activities?.forEach {
                routes.add(
                    ActivityRoute(key = it.key, path = it.value)
                )
            }

            val fragments = data["fragments"] as? Map<String, String>
            validateYaml(fragments ?: mapOf())
            fragments?.forEach {
                routes.add(
                    FragmentRoute(key = it.key, path = it.value)
                )
            }
        }
        return routes
    }

    /**
     * this function will throw an error when yaml contains duplicate path/value
     */
    @Throws(YAMLException::class)
    private fun validateYaml(sets: Map<String, String>) {
        val duplicates = hashSetOf<String>()
        for ((_, value) in sets) {
            if (duplicates.contains(value)) {
                throw YAMLException("Duplicate entries was found with value: $value, please remove it")
            }
            duplicates.add(value)
        }
    }
}
