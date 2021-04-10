package com.myapp.ui.cards

import moxy.InjectViewState
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.MvpPresenter
import moxy.MvpView
import com.rsastack.system.navigation.FlowRouter
import javax.inject.Inject


@StateStrategyType(AddToEndSingleStrategy::class)
interface CardsView : MvpView {

}

@InjectViewState
class CardsPresenter @Inject constructor(
    private val router: FlowRouter
) : MvpPresenter<CardsView>() {

    fun onBackPressed() {
        router.exit()
    }

}
