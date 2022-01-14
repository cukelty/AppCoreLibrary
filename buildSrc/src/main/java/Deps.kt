/**
 * this class manager all deps in project
 */
object AppVersion {
    const val versionCode = 214
    const val versionName = "V0.9.76"
    const val appSourceCompatibility = 1.8
    const val appTargetCompatibility = 1.8
}

object BuildVersion {
    const val compileSdkVersion = 29
    const val buildToolsVersion = "29.0.3"
    const val minSdkVersion = 21
    const val targetSdkVersion = 26
    const val appTargetSdkVersion = 26
}

object Versions {
    const val kotlin_version = "1.6.10"
    const val hiltVersion = "2.33-beta"
    const val kotlin_coroutines_version = "1.5.2"

}

object JetPack {
    const val multidex = "androidx.multidex:multidex:2.0.1"

}

object Libs {
    const val zxing = "com.google.zxing:core:3.3.3"
    const val eventbus = "org.greenrobot:eventbus:3.2.0"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"

    const val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines_version}"
    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines_version}"

}

object ClassPath {
    const val butterknife_plugin = "com.jakewharton:butterknife-gradle-plugin:10.2.3"
    const val dokit_plugin = "io.github.didi.dokit:dokitx-plugin:3.5.0.1-beta01"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
}