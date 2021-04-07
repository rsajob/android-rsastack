package com.rsastack.system.navigation

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Forward

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

    fun backToAndNavigateTo(backToScreen:Screen, navToScreen:Screen)
    {
        executeCommands(
            BackTo(backToScreen),
            Forward(navToScreen)
        )
    }

}