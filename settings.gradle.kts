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
include(":app-embedded")
include(":rsastack:core")
include(":rsastack:mvp")
include(":rsastack:mvvm")
include(":rsastack:mvp-validation")
include(":rsastack:rx")
include(":uicore")
