package com.myapp.ui.maintabs

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.rsastack.system.navigation.FlowRouter
import com.myapp.ui.Screens
import javax.inject.Inject


@StateStrategyType(AddToEndSingleStrategy::class)
interface MainTabsView : MvpView {
}

@InjectViewState
class MainTabsPresenter @Inject constructor(
    private val router: com.rsastack.system.navigation.FlowRouter
) : MvpPresenter<MainTabsView>() {

    fun onBackPressed() {
        router.exit()
    }

}
