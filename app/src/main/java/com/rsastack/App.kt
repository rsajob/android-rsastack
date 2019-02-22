package com.rsastack

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.rsastack.toothpick.AppModule
import com.rsastack.toothpick.DI
import com.rsastack.toothpick.NetworkModule
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
            AppModule(this)
        )

        val serverScope = Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
        serverScope.installModules(
            NetworkModule()
        )

    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        }
    }
}