buildscript {

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven (url = "https://jitpack.io")
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
