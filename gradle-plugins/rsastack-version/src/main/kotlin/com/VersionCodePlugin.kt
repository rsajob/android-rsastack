package com

import org.gradle.api.Plugin
import org.gradle.api.Project

class VersionCodePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // no-op
    }
}

/**
 * Builds an Android version code from the version of the project.
 * This is designed to handle the -SNAPSHOT -RC -beta and -alpha format.
 *
 * I.e. during development the version ends with -SNAPSHOT. As the code stabilizes and release nears
 * one or many Release Candidates are tagged. These all end with "-alpha1", "-beta2", "-RC1" etc.
 * And the final release is without any suffix.
 * @return
 */
fun buildVersionCode(versionName:String):Int
{
    // The rules is as follows:
    // -SNAPSHOT counts as 0
    // -alpha* counts as the APLHA number, i.e. 1  to 30
    // -beta*  counts as the BETA  number, i.e. 31 to 70
    // -RC*    counts as the RC    number, i.e. 71 to 98
    // final release counts as 99.
    // Thus you can only have 98 Release Candidates, which ought to be enough for everyone

    data class PatchResult(
        val patchVer:Int,
        val candidate:Int
    )

    fun splitCandidate(str:String, delimer:String, inc:Int):PatchResult?
    {
        val lst = str.split(delimer)
        if (lst.size == 2)
        {
            val patchVer = lst[0].toIntOrNull()
            val subVer = lst[1].toIntOrNull()
            if (patchVer != null && subVer != null)
                return PatchResult(patchVer, subVer + inc)
        }
        return null
    }

    val list = versionName.replace("-", "").split(".")
    val major = list.getOrNull(0) ?: "0"
    val minor = list.getOrNull(1) ?: "0"
    var patch = (list.getOrNull(2) ?: "0").toLowerCase()

    val patchRes: PatchResult?
    if (patch.endsWith("snapshot")) {
        patch = patch.replace("[^0-9]".toRegex(),"") // Clear all non numbers
        patchRes = PatchResult(patch.toInt(), 0)
    }else
        patchRes = splitCandidate(patch, "alpha", inc = 0 ) ?:
                splitCandidate(patch, "beta",  inc = 30) ?:
                splitCandidate(patch, "rc",    inc = 70) ?:
                PatchResult(patch.toInt(), 99)

    val verCode = (major.toInt() * 1000000) + (minor.toInt() * 10000) + (patchRes.patchVer * 100) + patchRes.candidate

    println("VersionName:$versionName")
    println("VersionCode:$verCode")

    return verCode
}
