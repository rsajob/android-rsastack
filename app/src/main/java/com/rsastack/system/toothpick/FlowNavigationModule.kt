package com.rsastack.system.toothpick

import com.rsastack.system.navigation.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class FlowNavigationModule(appRouter: Router) : Module() {
    init {
        val cicerone = Cicerone.create(FlowRouter(appRouter))
        bind(FlowRouter::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}