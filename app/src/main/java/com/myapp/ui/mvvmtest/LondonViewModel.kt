package com.myapp.ui.mvvmtest

import androidx.lifecycle.ViewModel
import com.rsastack.system.utils.debug

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
}