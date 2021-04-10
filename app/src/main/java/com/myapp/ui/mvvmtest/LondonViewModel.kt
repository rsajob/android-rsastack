package com.myapp.ui.mvvmtest

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.myapp.toothpick.DI
import com.myapp.ui.Screens
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.utils.debug
import toothpick.Toothpick

class LondonViewModel(finalScore: Int): ViewModel() {
    // The current score
    var score = finalScore

    init {
        debug("LondonViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        debug("LondonViewModel destroyed!")
    }

    fun pressNext() {
        val router = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(FlowRouter::class.java)
        router.navigateTo(Screens.AuthFlow())
    }

    fun pressBack() {
        val router = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(FlowRouter::class.java)
        router.exit()
    }
}