package com.rsastack.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.singleactivity.FlowFragment
import com.rsastack.system.toothpick.flowModule
import com.rsastack.system.toothpick.initDynamicUiScope
import com.rsastack.toothpick.DI
import com.rsastack.ui.Screens
import com.rsastack.system.utils.setupKeyboardModeResize
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class AuthFlowFragment: FlowFragment(), MvpView {

    private val scopeName:String by initDynamicUiScope { realScopeName ->
        DI.AUTH_FLOW_SCOPE = realScopeName // Save the dynamic scope name
        val scope = Toothpick.openScopes(DI.TOP_FLOW_SCOPE, DI.AUTH_FLOW_SCOPE)
        scope.installModules(
            flowModule(scope.getInstance(FlowRouter::class.java))
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
        private val router: com.rsastack.system.navigation.FlowRouter
): MvpPresenter<MvpView>()
{
    fun onExit() {
        router.finishFlow()
    }
}