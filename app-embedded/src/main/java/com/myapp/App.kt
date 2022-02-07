package com.myapp

import android.app.Application
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
    }
    
    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        }
    }
}