package com.rsastack.system.singleactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpView
import com.rsastack.system.navigation.BackButtonListener
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import com.rsastack.R
import javax.inject.Inject

/**
 * Created by Roman Savelev (aka @rsa) on 17.12.2018
 */
abstract class BaseSingleAppActivity: MvpAppCompatActivity(), MvpView
{
    val layoutRes: Int = R.layout.layout_flow_container
    val containerId: Int = R.id.flow_container

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val currentFragment: BackButtonListener?
        get() = supportFragmentManager.findFragmentById(containerId) as? BackButtonListener

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, containerId) {

            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ){
                //fix incorrect order lifecycle callback of MainFlowFragment
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

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
