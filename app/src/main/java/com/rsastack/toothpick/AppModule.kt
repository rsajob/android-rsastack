package com.rsastack.toothpick

import android.content.Context
import com.google.gson.Gson
import com.rsastack.system.rx.RxSchedulers
import com.rsastack.system.rx.SchedulersProvider
import com.rsastack.system.toothpick.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

fun appModule(context: Context) = module {
    //Global
    bind(Context::class.java).toInstance(context)
    bind(Gson::class.java).toInstance(Gson())

    // Navigation
    val cicerone = Cicerone.create()
    bind(Router::class.java).toInstance(cicerone.router)
    bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)

    bind(SchedulersProvider::class.java).toInstance(RxSchedulers())
}

