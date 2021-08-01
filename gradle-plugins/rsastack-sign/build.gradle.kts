plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

gradlePlugin {
    plugins.register("rsastack-sign-plugin") {
        id = "rsastack-sign-plugin"
        implementationClass = "com.SignPlugin"
    }
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(localGroovy())

    compileOnly("com.android.tools.build:gradle:7.0.0")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

