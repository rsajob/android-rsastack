package com.myapp.ui.splash

import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import com.myapp.domain.interactors.AuthInteractor
import com.rsastack.system.navigation.FlowRouter
import com.myapp.ui.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@StateStrategyType(SingleStateStrategy::class)
interface SplashView : MvpView {
    fun showProgress()
    fun showError(msg:String)
}

@InjectViewState
class SplashPresenter @Inject constructor(
    val router: FlowRouter,
    private val authInteractor: AuthInteractor
) : MvpPresenter<SplashView>() {

    override fun onFirstViewAttach() {
        start()
    }

    private fun start() {
        GlobalScope.launch(Dispatchers.Main) {
            viewState.showProgress()
            delay(1000)
            toNextScreen()
        }
    }

    private fun toNextScreen() {
        if (!authInteractor.isLoggedIn())
            router.newRootScreen(Screens.AuthFlow())
        else
            router.newRootScreen(Screens.MainTabs())
    }

    fun onRetry(){
        start()
    }

    fun doNext() {
        router.newRootScreen(Screens.AuthFlow())
    }

    fun onBack() {
        router.exit()
    }
}