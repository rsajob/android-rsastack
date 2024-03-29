package com.rsastack.system.mvp

import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.rsastack.mvp.R
import com.rsastack.system.navigation.BackButtonListener
import moxy.MvpView
import javax.inject.Inject

/**
 * Created by Roman Savelev (aka @rsa) on 17.12.2018
 */
abstract class MvpSingleAppActivity: MvpActivity(), MvpView
{
    val layoutRes: Int = R.layout.layout_flow_container
    val navigationContainerId: Int = R.id.flow_container

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val currentFragment: BackButtonListener?
        get() = supportFragmentManager.findFragmentById(navigationContainerId) as? BackButtonListener

    private val navigator: Navigator = AppNavigator(this, navigationContainerId)

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

}
