import com.buildVersionCode
import com.fromPath

plugins {
    id("com.android.application")
    id("rsastack-version-plugin")
    id("rsastack-sign-plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.rsastack.mvp"

        minSdkVersion(21)
        targetSdkVersion(30)

        val verName = "1.0.0-SNAPSHOT"
        val verCode = buildVersionCode(verName)

        versionName = verName
        versionCode = verCode

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    buildFeatures {
        viewBinding = true
    }

    signingConfigs {

        create("release") {
            fromPath(project, "/../sign/release/")
        }

        getByName("debug") {
            fromPath(project, "/../sign/debug/")
        }

    }

    flavorDimensions("default")

    buildTypes {

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
        }

        create("staging") {
            isDebuggable = false
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".staging"
            // but in proguard-rules-dontobfuscate.pro add '-dontobfuscate'
            // see https://stackoverflow.com/questions/47893266/build-failing-on-play-services11-8-x-with-pro-guard-parser-error
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules-dontobfuscate.pro")
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {

    implementation(project(":rsastack"))
    implementation(project(":uicore"))

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:1.4.3")

    // AndroidX
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.activity:activity-ktx:1.2.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0-beta01")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation("androidx.preference:preference-ktx:1.1.1")

    // Android Arch
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    // Material componemts
    implementation("com.google.android.material:material:1.3.0")

    // For simplify View Binding
    implementation("com.github.kirich1409:viewbindingpropertydelegate:1.4.4")

    // RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
//    implementation 'com.jakewharton.rxrelay2:rxrelay:2.1.1'
//    implementation 'com.jakewharton.rxbinding2:rxbinding:2.2.0'

    // Moxy
    implementation("com.github.moxy-community:moxy:2.1.1")
    kapt("com.github.moxy-community:moxy-compiler:2.1.1")

    // Cicerone (Navigation pattern)
    implementation("com.github.terrakok:cicerone:7.0")

    // Toothpick
    implementation("com.github.stephanenicolas.toothpick:toothpick-runtime:3.1.0")
    implementation("com.github.stephanenicolas.toothpick:ktp:3.1.0")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:3.1.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("ru.gildor.coroutines:kotlin-coroutines-retrofit:1.1.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // ToggleButtonLayout for stuff screen
    implementation("com.github.savvyapps:ToggleButtonLayout:1.2.0")

    // adapterdelegates4
    implementation("com.hannesdorfmann:adapterdelegates4:4.3.0") { isTransitive = false }

    // Junit
    testImplementation("junit:junit:4.13.2")
    // Core library
    androidTestImplementation("androidx.test:core:1.3.0")
    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test:rules:1.3.0")
    // Assertion simplify
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.ext:truth:1.3.0")
    androidTestImplementation("com.google.truth:truth:1.0")
    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.3.0")
}
