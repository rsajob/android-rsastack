package com.myapp.ui.maintabs

import moxy.InjectViewState
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.MvpPresenter
import moxy.MvpView
import com.rsastack.system.navigation.FlowRouter
import com.myapp.ui.Screens
import javax.inject.Inject


@StateStrategyType(AddToEndSingleStrategy::class)
interface MainTabsView : MvpView {
}

@InjectViewState
class MainTabsPresenter @Inject constructor(
    private val router: FlowRouter
) : MvpPresenter<MainTabsView>() {

    fun onBackPressed() {
        router.exit()
    }

}
