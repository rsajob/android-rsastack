package com.rsastack.system.toothpick

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.rsastack.system.navigation.FlowRouter
import toothpick.ktp.binding.module


fun flowModule(appRouter: Router) = module {
    val cicerone = Cicerone.create(FlowRouter(appRouter))
    bind(FlowRouter::class.java).toInstance(cicerone.router)
    bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())
}