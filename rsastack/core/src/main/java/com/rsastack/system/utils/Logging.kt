package com.rsastack.system.utils

import android.annotation.SuppressLint
import android.util.Log
import com.rsastack.core.BuildConfig
import com.rsastack.system.utils.LoggerSettings.isLogEnabled
import com.rsastack.system.utils.LoggerSettings.logPrefix

object LoggerSettings {
    var logPrefix = "deit/"
    var isLogEnabled = BuildConfig.BUILD_TYPE != "release"
    var showSourceClassName = true
}

@SuppressLint("LogNotTimber")
fun Any.debug(message: String, throwable: Throwable? = null) {
    if (isLogEnabled) {
        val sourceClassName = if (LoggerSettings.showSourceClassName) {if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name} else ""
        Log.d(
            logPrefix + sourceClassName,
            message,
            throwable
        )
    }
}
@SuppressLint("LogNotTimber")
fun Any.info(message: String, throwable: Throwable? = null) {
    if (isLogEnabled)
    {
        val sourceClassName = if (LoggerSettings.showSourceClassName) {if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name} else ""
        Log.i(
            logPrefix + sourceClassName,
            message ,
            throwable
        )
    }
}

@SuppressLint("LogNotTimber")
fun Any.warn(message: String, throwable: Throwable? = null) {
    if (isLogEnabled) {
        val sourceClassName = if (LoggerSettings.showSourceClassName) {if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name} else ""
        Log.w(
            logPrefix + sourceClassName,
            message,
            throwable
        )
    }
}

@SuppressLint("LogNotTimber")
fun Any.verbose(message: String, throwable: Throwable? = null) {
    if (isLogEnabled) {
        val sourceClassName = if (LoggerSettings.showSourceClassName) {if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name} else ""
        Log.v(
            logPrefix + sourceClassName,
            message,
            throwable
        )
    }
}

@SuppressLint("LogNotTimber")
fun Any.err(message: String, throwable: Throwable? = null) {
    if (isLogEnabled){
        val sourceClassName = if (LoggerSettings.showSourceClassName) {if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name} else ""
        Log.e(
            logPrefix + sourceClassName,
            logPrefix + message,
            throwable
        )
    }
}

@SuppressLint("LogNotTimber")
fun Any.err(throwable: Throwable) {
    if (isLogEnabled) {
        val sourceClassName = if (LoggerSettings.showSourceClassName) {if (javaClass.simpleName.isNotEmpty()) javaClass.simpleName else javaClass.name} else ""
        val message = " ${throwable.javaClass.simpleName} ${throwable.message}"
        Log.e(
            logPrefix + sourceClassName,
            logPrefix + message,
            throwable
        )
    }
}