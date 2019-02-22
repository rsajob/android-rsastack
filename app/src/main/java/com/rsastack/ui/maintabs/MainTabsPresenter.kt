package com.rsastack.ui.maintabs

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.ui.Screens
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
