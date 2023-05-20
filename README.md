# Router - Activity / Fragment Navigation between Modules
# How to Use?

### Pre-Initialization Gradle Dependency
```
soon to be
```

### 1. Prepare your `yourScene.yaml` file
your *.yaml* must contains 1 attributes at least
`activities` or `fragments`
<br> example:
#### DO
```yaml
activities:
  your-activity1: com.your.domain.MainActivity
  your-activity2: com.your.domain.HomeActivity
  
fragments:
  fragment1: com.spoonart.fragment1
  fragment2: com.spoonart.fragment2
```
#### DON'T
when duplicate path found in the *.yaml*, an exception will thrown at **Runtime**
```yaml
activities:
  your-activity1: com.your.domain.MainActivity
  your-activity2: com.your.domain.HomeActivity
  
fragments:
  fragment1: com.spoonart.fragment1
  fragment2: com.spoonart.fragment2
```

### 2. Provides your dependency for injection using dagger/hilt method
#### a. Provides YamlReader instance
#### b. Provides Router instance
<br> example (following is using hilt):
```kotlin
    //yaml reader
    @Provides
    @Singleton
    fun provideYamlReader(
        @ApplicationContext context: Context,
    ) = YamlReader(
        assetManager = context.assets,
        fileName = "scene.yaml"
    )
    
    //router
    @Provides
    @Singleton
    fun provideRouter(yamlReader: YamlReader): Router {
        return RouterImpl(
            yamlReader = yamlReader
        )
    }
    
    //Note:
    //it is up to you in how to provide the object, the requirement is the `Router` object must be initialized as Singleton
```

### 3. (Optional) Extends your Activity with BaseRouterActivity
this is optional, as this Base class is only contains a router variable for simply implementation.
You can define the variable in your xxActivity with below
example:
```kotlin
    @AndroidEntryPoint //hilt android entry point
    class MainActivity : BaseRouterActivity() {
        @Inject
        lateinit var router: Router
        
        ...
    }
```

### 4. Define your RouterKey
a key that written in *.yaml* must be defined in kotlin class as well
you can use enum or any method that most convenience for you
```kotlin
    enum class RoutePaths(
        val routerKey: RouterKey,
    ) {
        MainScene(RouterKey.CREATE("main-activity")),
        SpoonArtScene(RouterKey.CREATE("spoonart-activity"))
    }
    
    "main-activity" or "spoonart-activity" is the key attribute that exist in *.yaml file
```

### 5. Navigate using the RouterExt in your AppCompatActivity
an extension is provided for better implementation
```kotlin
        //extension detail `RouterExt.kt`
        fun AppCompatActivity.navigate(
                //a router instance
                router: Router,

                //yaml scene id for activity / fragment
                routerKey: RouterKey,

                //bundle extra for data passing (optional)
                bundle: Bundle? = null,

                //when set to true, by default will finish the corresponding activity (caller),
                //unless you for custom RouterFactory
                //(optional)
                finishAfter: Boolean = false,

                //a flag command for the next activity (optional)
                flag: Int? = null,
          )


       // usage example
       btn.setOnClickListener {
            navigate(
                router = router,
                routerKey = RoutePaths.SpoonArtScene.routerKey,
                flag = Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }
```


# Bonus
### Use your Custom RouterFactory by extending BaseRouterFactory
you can freely control on how to redirect to your new scene (activity)

#### a. Actvity router implementation
```kotlin
//create YourRouterFactory (or anyname), must extends BaseRouterFactory
abstract class YourRouterFactory(
    override val routerKey: RouterKey,
    private val finishAfter: Boolean = false,
    private val flag: Int? = null,
) : BaseRouterFactory() {

    override fun transmit(clazzName: String) {
        //do something powerful with your super imagination
        //applying the activity transition with custom animation is possible here
    }
}

```

#### Following is Activity Factory that provided within this module
```kotlin
abstract class ActivityRouterFactory(
    override val routerKey: RouterKey,
    private val finishAfter: Boolean = false,
    private val flag: Int? = null,
) : BaseRouterFactory() {

    override fun transmit(clazzName: String) {
        try {
            //this is must, because we are going to pass it into the Intent
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


// initialize your Routerfactory like example below
val routerFactory = object : ActivityRouterFactory(
        routerKey = routerKey,
        finishAfter = finishAfter,
        flag = flag
    ) {
        override val context: Context
            get() = applicationContext
}

// In Your AppCompatActivity
// use this extension to navigate with the custom RouterFactory
navigate(
  router = router,
  routerFactory = routerFactory
)
```
