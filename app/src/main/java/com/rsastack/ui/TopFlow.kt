package com.rsastack.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.singleactivity.FlowFragment
import com.rsastack.system.toothpick.FlowNavigationModule
import com.rsastack.system.toothpick.ScopedMvpPresenter
import com.rsastack.system.toothpick.initDynamicScope
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

import com.rsastack.toothpick.DI

class TopFlowFragment: FlowFragment(), MvpView {

    private val scopeName:String by initDynamicScope { realScopeName ->
        DI.TOP_FLOW_SCOPE = realScopeName // Save the dynamic scope name
        val scope = Toothpick.openScopes(DI.SERVER_SCOPE, DI.TOP_FLOW_SCOPE)
        scope.installModules(
            FlowNavigationModule(scope.getInstance(Router::class.java))
        )
    }

    @InjectPresenter
    lateinit var presenter: TopFlowPresenter

    @ProvidePresenter
    fun providePresenter() =
            Toothpick.openScope(scopeName)
                    .getInstance(TopFlowPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(scopeName))

        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty())
            navigator.setLaunchScreen(Screens.Splash)
    }

    override fun onExit() {
        presenter.onExit()
    }

}


class TopFlowPresenter @Inject constructor(
        private val router: Router
): ScopedMvpPresenter<MvpView>()
{
    fun onExit() {
        router.exit()
    }
}