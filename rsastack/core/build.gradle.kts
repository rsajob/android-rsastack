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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    buildTypes {
        getByName("debug") { }
        create("staging") { }
        getByName("release") { }
    }

}

dependencies {

    // AndroidX
    implementation("androidx.core:core-ktx:1.5.0-rc01")
    implementation("androidx.appcompat:appcompat:1.3.0-rc01")
    implementation("androidx.activity:activity-ktx:1.2.3")

    implementation("androidx.recyclerview:recyclerview:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0-beta02")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation("androidx.preference:preference-ktx:1.1.1")

    // Cicerone (Navigation pattern)
    implementation("com.github.terrakok:cicerone:7.0")

    // Toothpick
    implementation("com.github.stephanenicolas.toothpick:toothpick-runtime:3.1.0")
    implementation("com.github.stephanenicolas.toothpick:ktp:3.1.0")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:3.1.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // adapter delegates
    implementation("com.hannesdorfmann:adapterdelegates4:4.3.0") { isTransitive = false }

}
