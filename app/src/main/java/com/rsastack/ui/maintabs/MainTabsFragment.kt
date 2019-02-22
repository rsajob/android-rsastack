package com.rsastack.ui.maintabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_main.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import com.rsastack.R
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.toothpick.DI
import com.rsastack.ui.Screens
import com.rsastack.ui.common.BaseFragment
import com.rsastack.utils.setupKeyboardModePan

import toothpick.Toothpick

class MainTabsFragment : BaseFragment(), MainTabsView {

    override val layoutRes = R.layout.fragment_main

    private val currentTabFragment: Fragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? Fragment

    @InjectPresenter
    lateinit var presenter: MainTabsPresenter

    @ProvidePresenter
    fun providePresenter(): MainTabsPresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(
        MainTabsPresenter::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupKeyboardModePan()
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId)
            {
                R.id.navigation_home -> selectTab(Screens.TabCall)
                R.id.navigation_cards -> selectTab(Screens.TabContacts)
            }
            true
        }

        // Default Tab
        selectTab(Screens.TabCall)
        bottom_navigation.selectedItemId = R.id.navigation_home
    }

    private fun selectTab(tab: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) add(R.id.tab_container, createTabFragment(tab), tab.screenKey)

            currentFragment?.let {
                hide(it)
                it.userVisibleHint = false
            }
            newFragment?.let {
                show(it)
                it.userVisibleHint = true
            }
        }.commitNow()
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun onBackPressed() {
        (currentTabFragment as? BackButtonListener)?.onBackPressed()
    }


}
