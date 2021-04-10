package com.myapp.ui

import android.os.Bundle
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.toothpick.flowModule
import com.rsastack.system.toothpick.initDynamicUiScope
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

import com.myapp.toothpick.DI
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.singleactivity.FlowFragment

class TopFlowFragment: FlowFragment(), MvpView {

    private val scopeName:String by initDynamicUiScope { realScopeName ->
        DI.TOP_FLOW_SCOPE = realScopeName // Save the dynamic scope name
        val scope = Toothpick.openScopes(DI.SERVER_SCOPE, DI.TOP_FLOW_SCOPE)
        scope.installModules(
            flowModule(scope.getInstance(Router::class.java))
        )
    }

    @InjectPresenter
    lateinit var presenter: TopFlowPresenter

    @ProvidePresenter
    fun providePresenter() = Toothpick.openScope(scopeName).getInstance(TopFlowPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(scopeName))

        super.onCreate(savedInstanceState)

        if (childFragmentManager.fragments.isEmpty())
            navigator.setLaunchScreen(Screens.Splash())
    }

    override fun onExit() {
        presenter.onExit()
    }

}


class TopFlowPresenter @Inject constructor(
        private val router: FlowRouter
): MvpPresenter<MvpView>()
{
    fun onExit() {
        router.finishFlow()
    }
}