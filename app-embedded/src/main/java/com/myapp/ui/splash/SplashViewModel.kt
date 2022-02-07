package com.myapp.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.interactors.AuthInteractor
import com.myapp.ui.Screens
import com.rsastack.system.navigation.FlowRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class State{
    object Progress : State()
    class Error(val message:String): State()
}

class SplashViewModel @Inject constructor(
    val router: FlowRouter,
    private val authInteractor: AuthInteractor
): ViewModel() {

    private val _state = MutableStateFlow<State>(State.Progress)
    val state: StateFlow<State> = _state

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.tryEmit(State.Progress)
            delay(1000)
            toNextScreen()
        }
    }

    private fun toNextScreen() {
        if (!authInteractor.isLoggedIn())
            router.newRootScreen(Screens.London())
        else
            router.newRootScreen(Screens.MainTabs())
    }

    fun onRetry(){
        start()
    }

    fun onBack() {
        router.exit()
    }

}