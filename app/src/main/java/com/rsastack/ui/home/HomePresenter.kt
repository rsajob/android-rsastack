package com.rsastack.ui.home

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.rsastack.domain.interactors.AuthInteractor
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.ui.Screens
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
        router.newRootScreen(Screens.AuthFlow)
    }

}
