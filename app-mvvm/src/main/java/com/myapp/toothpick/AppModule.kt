package com.myapp.toothpick

import android.content.Context
import com.rsastack.system.rx.RxSchedulers
import com.rsastack.system.rx.SchedulersProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import toothpick.ktp.binding.module

fun appModule(context: Context) = module {
    //Global
    bind(Context::class.java).toInstance(context)
    bind(SchedulersProvider::class.java).toInstance(RxSchedulers())
}

