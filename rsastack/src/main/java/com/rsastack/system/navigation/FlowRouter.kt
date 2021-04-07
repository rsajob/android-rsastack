package com.rsastack.system.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Forward

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
                Forward(screen, false)
        )
    }

    fun backToAndNavigateTo(backToScreen:Screen, navToScreen:Screen)
    {
        executeCommands(
            BackTo(backToScreen),
            Forward(navToScreen, false)
        )
    }

}