package com.myapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.myapp.toothpick.DI
import com.myapp.ui.Screens
import com.rsastack.system.mvvm.MvvmFlowFragment
import com.rsastack.system.mvvm.provideViewModel
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.toothpick.flowModule
import com.rsastack.system.toothpick.initDynamicUiScope
import com.rsastack.system.utils.setupKeyboardModeResize
import moxy.MvpView
import toothpick.Toothpick
import javax.inject.Inject

class AuthFlowFragment: MvvmFlowFragment(), MvpView {

    private val scopeName:String by initDynamicUiScope { realScopeName ->
        DI.AUTH_FLOW_SCOPE = realScopeName // Save the dynamic scope name
        val scope = Toothpick.openScopes(DI.TOP_FLOW_SCOPE, DI.AUTH_FLOW_SCOPE)
        scope.installModules(
            flowModule(scope.getInstance(FlowRouter::class.java))
        )
    }

    lateinit var viewModel: AuthFlowVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupKeyboardModeResize()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(scopeName))
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(scopeName, AuthFlowVM::class.java)

        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.AuthPhone())
        }
    }

    override fun onExit() {
        viewModel.onExit()
    }

}

class AuthFlowVM @Inject constructor(
        private val router: FlowRouter
): ViewModel()
{
    fun onExit() {
        router.finishFlow()
    }
}