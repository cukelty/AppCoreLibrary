# startup 使用方法介绍

startup 使用contentprovider 共享实现init 初始化自定义问题。

1. 引入依赖

 ```groovy
dependencies {
    implementation "androidx.startup:startup-runtime:1.0.0"
}
```

2.定义 Initializer

```kotlin
// Initializes WorkManager.
class WorkManagerInitializer : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }
    override fun dependencies(): List<Class<out Initializer<*>>> {
// No dependencies on other libraries.
        return emptyList()
    }
}
```

如果logger 依赖很多workmanager 那么久需要先初始化logger依赖的workmanager 如下

```kotlin
// Initializes ExampleLogger.
class ExampleLoggerInitializer : Initializer<ExampleLogger> {
    override fun create(context: Context): ExampleLogger {
        // WorkManager.getInstance() is non-null only after
        // WorkManager is initialized.
        return ExampleLogger(WorkManager.getInstance(context))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // Defines a dependency on WorkManagerInitializer so it can be
        // initialized after WorkManager is initialized.
        return listOf(WorkManagerInitializer::class.java)
    }
}

```

3.添加manifest

```xml

<provider android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup" android:exported="false"
    tools:node="merge">
    <!-- This entry makes ExampleLoggerInitializer discoverable. -->
    <meta-data android:name="com.example.ExampleLoggerInitializer"
        android:value="androidx.startup" />
</provider>


```

您不需要为WorkManagerInitializer添加<meta data>
条目，因为WorkManagerInitializer是ExampleLoggerInitializer的依赖项。这意味着，如果ExampleLoggerInitializer是可发现的，那么WorkManagerInitializer也是可发现的。
tools:node=“merge”属性确保清单合并工具正确解决任何冲突条目。

4. 手动初始化

如果不想在启动时自动初始化，而是在希望的地方才进行，可使用 AppInitializer 类手动实现延迟初始化：

延迟初始化：

```kotlin
AppInitializer.getInstance(this).initializeComponent(WorkManagerInitializer::class.java)
```
但前提是要禁止自动初始化：

```xml
<provider android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup" android:exported="false"
    tools:node="merge">
    <meta-data android:name="com.example.ExampleLoggerInitializer" tools:node="remove" />
</provider>
```

tools:node="remove" 会在合并 AndroidManifest 时将 android:name 为 "com.example.ExampleLoggerInitializer" 的
meta-data 删除。



