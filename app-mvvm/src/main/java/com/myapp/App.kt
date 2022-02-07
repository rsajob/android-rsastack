package com.myapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.myapp.toothpick.DI
import com.myapp.toothpick.appModule
import com.myapp.toothpick.networkModule
import com.rsastack.system.utils.debug
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initAppScope(Toothpick.openScope(DI.APP_SCOPE))
    }

    @VisibleForTesting
    fun initAppScope(appScope: Scope) {
        appScope.installModules(
            appModule(this)
        )

//        val serverScope = Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
//        serverScope.installModules(
//            networkModule()
//        )

    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        }
    }
}