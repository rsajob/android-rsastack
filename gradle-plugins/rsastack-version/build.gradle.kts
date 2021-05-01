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
    // Объявим пустой плагин, чтобы с помощью него можно было загрузить классы
    plugins.register("rsastack-version-plugin") {
        id = "rsastack-version-plugin"
        implementationClass = "com.VersionCodePlugin"
    }
    
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation("com.android.tools.build:gradle:4.2.0-rc01")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

