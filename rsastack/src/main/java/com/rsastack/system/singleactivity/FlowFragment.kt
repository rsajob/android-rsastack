package com.rsastack.system.singleactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Command
import com.rsastack.system.navigation.BackButtonListener
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.rsastack.R
import com.rsastack.system.moxy.MvpAppCompatFragment
import javax.inject.Inject

abstract class FlowFragment : MvpAppCompatFragment(),
    BackButtonListener {
    open val layoutRes: Int = R.layout.layout_flow_container
    open val containerId: Int = R.id.flow_container

    private val currentFragment
        get() = childFragmentManager.findFragmentById(containerId) as? BackButtonListener

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected val navigator: Navigator by lazy {
        object : AppNavigator(requireActivity(), containerId, childFragmentManager) {
            override fun activityBack() {
                onExit()
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutRes, container, false)

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: onExit()
    }

    open fun onExit() {}

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}