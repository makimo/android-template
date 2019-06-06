@file:Suppress("MayBeConstant", "SpellCheckingInspection",
    "MemberVisibilityCanBePrivate"
)

/**
 * [Dependencies.kt] specifies three objects: [Config], [Version] and [Deps],
 * which, in turn, are used to configure app/build.gradle and
 * [root]/build.gradle.
 *
 * XXX: If you find a bug in this config, or have a suggestion,
 * submit an issue to project "Prefabrykaty" with component "android-template"
 * and label "Gradle". (Outdated dependencies count too.) Submit to:
 *
 * @author iwo.herka@makimo.pl
 */

/**
 * This object is used to configure gradle build itself, as well as
 * options for build file.
 */
object Config {
    /**
     * At build time, Gradle generates the BuildConfig class so your app code
     * can inspect information about the current build. You can also add custom
     * fields to the BuildConfig class from your Gradle build configuration
     * file using the buildConfigField() method and access those values in your
     * app's runtime code.
     */
    val buildValues = listOf(
        "String", "API_URL",              "some.api.com/",
        "String", "API_PROTOCOL",         "https",
        "long",   "HTTP_READ_TIMEOUT",    "60",
        "long",   "HTTP_CONNECT_TIMEOUT", "60",
        "String", "PACKAGE",              Versions.applicationId
    )

    /**
     * The first line in the build configuration applies the Android/Kotlin
     * plugins for Gradle to current build and makes it possible to specify
     * Android/Kotlin-specific build options, or use platform-specific options
     * such as "kapt".
     */
    val plugins = listOf(
        "com.android.application",
        "kotlin-android",
        "kotlin-android-extensions",
        // Annotation processors (see JSR 269) are supported in Kotlin
        // with the kapt compiler plugin. Allows to use "kapt" in dependencies.
        "kotlin-kapt"
    )

    /**
     * Convenience value for use in gradle. Creating [buildValues] with
     * literal maps would be too verbose.
     */
    val buildValuesIter = buildValues.chunked(3).map {
        mapOf("type" to it[0], "name" to it[1],
            "value" to if (it[0] == "String") "\"${it[2]}\"" else it[2])
    }
}

/**
 * This object is for all versioning in the Android project config.
 */
object Versions {
    // Project versioning

    /**
     * Internal version of the application, also checked
     * by Google Play on publish.
     */
    val versionCode = 1

    /**
     * Human-readable, client version. Must adhere to
     * semantic versioning (https://semver.org/).
     */
    val versionName = "1.0.0"

    /**
     * Every Android app has a unique application ID that
     * looks like a Java package name, such as com.example.myapp.
     * This ID uniquely identifies your app on the device and in Google Play Store.
     */
    val applicationId = "pl.makimo.androidtemplate"
    val packageName = applicationId

    // Android settings

    val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val jvmTarget = 1.8

    val minSdk     = 23
    val targetSdk  = 28
    val compileSdk = 28
    val kotlin     = "1.3.30"
    val kotlinStd  = "1.2.50"
    val gradle     = "3.4.1"
    val androidX   = "1.0.2"
    val constraint = "1.1.3"

    // Rx

    val rxAndroid = "2.1.1"
    val rxKotlin = "2.3.0"

    // Tests

    val junit      = "4.12"
    val testRunner = "1.1.1"
    val espresso   = "3.1.1"
    // Alows to mock Retrofit responses.
    val mockWebServer = "3.12.0"

    // Retrofit

    // Network communication library.
    val retrofit           = "2.5.0"
    // Interceptor is used to capture Retrofit logs in Logcat.
    val okttp3Interceptor  = "3.8.1"
    // Jackson is used to (de)serialize JSON by Retrofit.
    val jackson            = "2.3.0"
    val jacksonAnnotations = "2.9.8"

    // Dagger

    val dagger = "2.15"

    // Room

    val room = "2.0.0"
    // Allows to preview and modify database in the browser:
    // https://github.com/amitshekhariitbhu/Android-Debug-Database
    val roomDebug = "1.0.6"
}

/**
 * This object specifies dependencies (package-name + version) for use in 'dependencies'
 * section in app/build.gradle and 'buildscript' in [root]/build.gradle.
 */
object Deps {
    // Android
    val kotlin       = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinStd}"
    val gradle       = "com.android.tools.build:gradle:${Versions.gradle}"

    // AppCompat
    val androidXCore      = "androidx.core:core-ktx:${Versions.androidX}"
    val androidXAppCompat = "androidx.appcompat:appcompat:${Versions.androidX}"
    val constraintLayout  = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

    // Rx
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val rxKotlin  = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"

    // Tests
    val junit            = "junit:junit:${Versions.junit}"
    val androidXRules    = "androidx.test:rules:${Versions.testRunner}"
    val androidXRunner   = "androidx.test:runner:${Versions.testRunner}"
    val androidXEspresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val mockWebServer    = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"

    // Retrofit
    val retrofit2          = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val okhttp3Interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okttp3Interceptor}"
    val jackson            = "com.squareup.retrofit2:converter-jackson:${Versions.jackson}"
    val jacksonAnnotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jacksonAnnotations}"

    // Dagger 2
    val dagger               = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val daggerCompiler       = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val daggerProcessor      = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    // Room
    val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.room}"
    val roomCompiler       = "android.arch.persistence.room:compiler:${Versions.room}"
    val roomRuntime        = "android.arch.persistence.room:runtime:${Versions.room}"
    val roomTesting        = "android.arch.persistence.room:testing:${Versions.room}"
    val roomDebug          = "com.amitshekhar.android:debug-db:${Versions.roomDebug}"
}