pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

includeBuild("gradle-plugins/rsastack-sign")
includeBuild("gradle-plugins/rsastack-version")

include(":app-mvp")
include(":app-mvvm")
include(":rsastack")
include(":uicore")
