package com.rsastack.system.navigation

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.BackTo

class FlowRouter(val appRouter: Router) : Router() {

    fun startFlow(screen: Screen) {
        appRouter.navigateTo(screen)
    }

    fun newRootFlow(screen: Screen) {
        appRouter.newRootScreen(screen)
    }

    fun finishFlow() {
        appRouter.exit()
    }

    fun newScreenChain(screen: Screen)
    {
        executeCommands(
                BackTo(null),
                Forward(screen)
        )
    }

}