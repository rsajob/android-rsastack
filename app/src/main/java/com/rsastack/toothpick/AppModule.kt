package com.rsastack.toothpick

import android.content.Context
import com.google.gson.Gson
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        //Global
        this.bind(Context::class.java).toInstance(context)
        this.bind(Gson::class.java).toInstance(Gson())

        // Navigation
        val cicerone = Cicerone.create()
        this.bind(Router::class.java).toInstance(cicerone.router)
        this.bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}