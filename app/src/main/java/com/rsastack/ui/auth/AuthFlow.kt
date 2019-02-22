package com.rsastack.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.singleactivity.FlowFragment
import com.rsastack.system.toothpick.FlowNavigationModule
import com.rsastack.system.toothpick.ScopedMvpPresenter
import com.rsastack.system.toothpick.initDynamicScope
import com.rsastack.toothpick.DI
import com.rsastack.ui.Screens
import com.rsastack.utils.setupKeyboardModeResize
import toothpick.Toothpick
import javax.inject.Inject

class AuthFlowFragment: FlowFragment(), MvpView {

    private val scopeName:String by initDynamicScope { realScopeName ->
        DI.AUTH_FLOW_SCOPE = realScopeName // Save the dynamic scope name
        val scope = Toothpick.openScopes(DI.TOP_FLOW_SCOPE, DI.AUTH_FLOW_SCOPE)
        scope.installModules(
            FlowNavigationModule(scope.getInstance(FlowRouter::class.java))
        )
    }

    @InjectPresenter
    lateinit var presenter: AuthFlowPresenter

    @ProvidePresenter
    fun providePresenter() = Toothpick.openScope(scopeName).getInstance(AuthFlowPresenter::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupKeyboardModeResize()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(scopeName))
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.AuthPhone)
        }
    }

    override fun onExit() {
        presenter.onExit()
    }

}


class AuthFlowPresenter @Inject constructor(
        private val router: FlowRouter
): ScopedMvpPresenter<MvpView>()
{
    fun onExit() {
        router.finishFlow()
    }
}