package com.spoonart.routeractivity

import android.content.Context
import com.spoonart.painter.impl.Painter
import com.spoonart.painter.impl.PainterImpl
import com.spoonart.router.implementation.Router
import com.spoonart.router.implementation.RouterImpl
import com.spoonart.router.utils.YamlReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun providePainter(): Painter {
        return PainterImpl()
    }

    @Provides
    @Singleton
    fun provideYamlReader(
        @ApplicationContext context: Context,
    ) = YamlReader(
        assetManager = context.assets,
        fileName = "scene.yaml"
    )

    @Provides
    @Singleton
    fun provideRouter(yamlReader: YamlReader): Router {
        return RouterImpl(
            yamlReader = yamlReader
        )
    }

}
