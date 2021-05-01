package com

import com.android.build.api.dsl.SigningConfig
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.file.impl.DefaultFileMetadata.file
import org.gradle.kotlin.dsl.get
import java.io.File
import java.io.FileInputStream
import java.util.*

class SignPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // no-op
    }
}

fun openProperties(file: File): Properties
{
    val properties = Properties()
    if (file.exists())
        properties.load(FileInputStream(file))
    return properties
}

fun SigningConfig.fromPath(project:Project, path:String)
{
    val path = File(project.buildDir.absolutePath+"/../"+path).absolutePath
    val prop = openProperties(File("$path/data.properties"))
    storeFile = File("$path/keystore.jks")
    storePassword = prop.getProperty("keystorePassword")
    keyAlias = prop.getProperty("keyAlias")
    keyPassword = prop.getProperty("keyPassword")
}