package com.myapp.ui.home

import moxy.InjectViewState
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.MvpPresenter
import moxy.MvpView
import com.myapp.domain.interactors.AuthInteractor
import com.rsastack.system.navigation.FlowRouter
import com.myapp.ui.Screens
import javax.inject.Inject

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeView : MvpView {

}

@InjectViewState
class HomePresenter @Inject constructor(
    private val router: FlowRouter,
    private val authInteractor: AuthInteractor
) : MvpPresenter<HomeView>() {

    fun onBackPressed() {
        router.exit()
    }

    fun pressLogout() {
        authInteractor.logout()
        router.newRootScreen(Screens.AuthFlow())
    }

}
