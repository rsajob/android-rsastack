plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30

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
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.activity:activity-ktx:1.4.0")

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
