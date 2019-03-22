package com.rsastack.ui.cards

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.rsastack.system.navigation.FlowRouter
import javax.inject.Inject


@StateStrategyType(AddToEndSingleStrategy::class)
interface CardsView : MvpView {

}

@InjectViewState
class CardsPresenter @Inject constructor(
    private val router: com.rsastack.system.navigation.FlowRouter
) : MvpPresenter<CardsView>() {

    fun onBackPressed() {
        router.exit()
    }

}
