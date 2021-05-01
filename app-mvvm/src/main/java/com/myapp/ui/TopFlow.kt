package com.myapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.toothpick.flowModule
import com.rsastack.system.toothpick.initDynamicUiScope
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

import com.myapp.toothpick.DI
import com.rsastack.system.viewmodel.provideViewModel
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.singleactivity.MvvmFlowFragment

class TopFlowFragment: MvvmFlowFragment() {

    private val scopeName:String by initDynamicUiScope { realScopeName ->
        DI.TOP_FLOW_SCOPE = realScopeName // Save the dynamic scope name
        val scope = Toothpick.openScopes(DI.SERVER_SCOPE, DI.TOP_FLOW_SCOPE)
        scope.installModules(
            flowModule(scope.getInstance(Router::class.java))
        )
    }

    private lateinit var viewModel: TopFlowViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        viewModel = provideViewModel(scopeName, TopFlowViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(scopeName))

        super.onCreate(savedInstanceState)

        if (childFragmentManager.fragments.isEmpty())
            navigator.setLaunchScreen(Screens.Splash())
    }

    override fun onExit() {
        viewModel.onExit()
    }

}


class TopFlowViewModel @Inject constructor(
        private val router: FlowRouter
): ViewModel()
{
    fun onExit() {
        router.finishFlow()
    }
}