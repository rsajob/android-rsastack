package com.rsastack.system.singleactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsastack.system.navigation.BackButtonListener
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
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
        object : SupportAppNavigator(activity, childFragmentManager, containerId) {
            override fun activityBack() {
                onExit()
            }

            override fun setupFragmentTransaction(
                    command: Command,
                    currentFragment: Fragment?,
                    nextFragment: Fragment?,
                    fragmentTransaction: FragmentTransaction)
            {
                //fix incorrect order lifecycle callback of MainFlowFragment
                fragmentTransaction.setReorderingAllowed(true)
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