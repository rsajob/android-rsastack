buildscript {

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven (url = "https://jitpack.io")
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
