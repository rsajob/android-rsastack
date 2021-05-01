package com

import com.android.build.api.dsl.SigningConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.lang.IllegalArgumentException
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
    val signPath = File("${project.projectDir}/$path").canonicalPath
    val filePath = File(signPath)
    if (!filePath.exists())
        throw IllegalArgumentException("Bad sign path $signPath")
    
    val prop = openProperties(File("${filePath.absolutePath}/data.properties"))
    storeFile = File("${filePath.absolutePath}/keystore.jks")
    storePassword = prop.getProperty("keystorePassword")
    keyAlias = prop.getProperty("keyAlias")
    keyPassword = prop.getProperty("keyPassword")
}