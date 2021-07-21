plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") { }
        create("staging") { }
        getByName("release") { }
    }

}

dependencies {

    implementation(project(":rsastack:core"))

    // AndroidX
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.activity:activity-ktx:1.2.3")

    // Moxy
    implementation("com.github.moxy-community:moxy:2.2.2")
    kapt("com.github.moxy-community:moxy-compiler:2.2.2")

    // Toothpick
    implementation("com.github.stephanenicolas.toothpick:toothpick-runtime:3.1.0")
    implementation("com.github.stephanenicolas.toothpick:ktp:3.1.0")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:3.1.0")

    // Cicerone (Navigation pattern)
    implementation("com.github.terrakok:cicerone:7.1")

}
