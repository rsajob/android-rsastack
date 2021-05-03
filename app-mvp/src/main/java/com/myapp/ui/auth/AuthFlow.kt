package com.myapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentAuthFlowBinding
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.mvp.MvpFlowFragment
import com.rsastack.system.toothpick.flowModule
import com.rsastack.system.toothpick.initDynamicUiScope
import com.myapp.toothpick.DI
import com.myapp.ui.Screens
import com.rsastack.system.utils.setupKeyboardModeResize
import toothpick.Toothpick
import javax.inject.Inject

class AuthFlowFragment: MvpFlowFragment(), MvpView {

    override val layoutRes = R.layout.fragment_auth_flow
    override val containerId = R.id.content_container

    private val binding by viewBinding(FragmentAuthFlowBinding::bind)

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
            navigator.setLaunchScreen(Screens.AuthPhone())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = "Auth Flow"
    }

    override fun onExit() {
        presenter.onExit()
    }

}


class AuthFlowPresenter @Inject constructor(
        private val router: FlowRouter
): MvpPresenter<MvpView>()
{
    fun onExit() {
        router.finishFlow()
    }
}