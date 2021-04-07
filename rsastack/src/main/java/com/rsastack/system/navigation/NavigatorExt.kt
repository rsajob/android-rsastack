package com.rsastack.system.navigation

import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppScreen
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Replace

fun Navigator.setLaunchScreen(screen: AppScreen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}
