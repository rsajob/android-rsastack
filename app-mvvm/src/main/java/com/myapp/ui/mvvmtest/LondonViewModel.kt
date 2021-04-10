package com.myapp.ui.mvvmtest

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.myapp.toothpick.DI
import com.myapp.ui.Screens
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.utils.debug
import toothpick.Toothpick
import javax.inject.Inject

data class ScoreHolder(val finalScore:Int)

class LondonViewModel @Inject constructor (
    private val router: FlowRouter,
    private val scoreHolder: ScoreHolder
): ViewModel() {
    // The current score
    var score = scoreHolder.finalScore

    init {
        debug("LondonViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        debug("LondonViewModel destroyed!")
    }

    fun pressNext() {
        router.navigateTo(Screens.AuthFlow())
    }

    fun pressBack() {
        router.exit()
    }
}