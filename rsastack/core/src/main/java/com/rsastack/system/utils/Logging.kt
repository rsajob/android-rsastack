package com.rsastack.system.utils

import android.annotation.SuppressLint
import android.util.Log
import com.rsastack.core.BuildConfig
import com.rsastack.system.utils.LoggerSettings.isLogEnabled
import com.rsastack.system.utils.LoggerSettings.logPrefix

object LoggerSettings {
    var logPrefix = "deit/"
    var isLogEnabled = BuildConfig.BUILD_TYPE != "release"
}

@SuppressLint("LogNotTimber")
fun Any.debug(message: String, throwable: Throwable? = null) {
    if (isLogEnabled)
    Log.d(
        logPrefix + if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name,
        message ,
        throwable
    )
}
@SuppressLint("LogNotTimber")
fun Any.info(message: String, throwable: Throwable? = null) {
    if (isLogEnabled)
    Log.i(
        logPrefix + if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name,
        message ,
        throwable
    )
}

@SuppressLint("LogNotTimber")
fun Any.warn(message: String, throwable: Throwable? = null) {
    if (isLogEnabled)
    Log.w(
        logPrefix + if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name,
        message,
        throwable
    )
}

@SuppressLint("LogNotTimber")
fun Any.verbose(message: String, throwable: Throwable? = null) {
    if (isLogEnabled)
    Log.v(
        logPrefix + if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name,
        message,
        throwable
    )
}

@SuppressLint("LogNotTimber")
fun Any.err(message: String, throwable: Throwable? = null) {
    if (isLogEnabled)
    Log.e(
        logPrefix + if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name,
        logPrefix + message,
        throwable
    )
}

@SuppressLint("LogNotTimber")
fun Any.err(throwable: Throwable) {
    if (isLogEnabled) {
        val message = " ${throwable.javaClass.simpleName} ${throwable.message}"
        Log.e(
                logPrefix + if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name,
                logPrefix + message,
                throwable
        )
    }
}