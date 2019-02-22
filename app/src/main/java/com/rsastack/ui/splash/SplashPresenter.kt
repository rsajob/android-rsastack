package com.rsastack.ui.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rsastack.domain.interactors.AuthInteractor
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.ui.Screens
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
            delay(2000)
            toNextScreen()
        }
    }

    private fun toNextScreen() {
        if (!authInteractor.isLoggedIn())
            router.newRootScreen(Screens.AuthFlow)
        else
            router.newRootScreen(Screens.MainTabs)
    }

    fun onRetry(){
        start()
    }

    fun doNext() {
        router.newRootScreen(Screens.AuthFlow)
    }

    fun onBack() {
        router.exit()
    }
}