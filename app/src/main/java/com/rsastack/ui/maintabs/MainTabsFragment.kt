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

private const val CURRENT_TAB = "current_tab"

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

    private var currentTab:String? = null

    override fun onSaveInstanceState(outState: Bundle) {
        currentTab?.let { outState.putString(CURRENT_TAB, it) }
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null)
            currentTab = savedInstanceState.getString(CURRENT_TAB, null)

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

        // init containers
        val tabs = listOf(Screens.TabCall, Screens.TabContacts)
        val fm = childFragmentManager
        tabs.forEach { tab ->
            val fragment = createTabFragment(tab)
            fm.beginTransaction()
                .add(R.id.tab_container, fragment, tab.screenKey)
                .detach(fragment)
                .commitNow()
        }

        // Default Tab
        if (currentTab == null)
            currentTab = Screens.TabCall.screenKey

        currentTab?.let { selectTabByName(it) }
    }

    private fun selectTabByName(screenKey: String) {
        val screen = when(screenKey)
        {
            Screens.TabCall.screenKey -> Screens.TabCall
            Screens.TabContacts.screenKey -> Screens.TabContacts
            else -> Screens.TabCall
        }
        selectTab(screen)

        val itemId = when(screenKey)
        {
            Screens.TabCall.screenKey -> R.id.navigation_home
            Screens.TabContacts.screenKey -> R.id.navigation_cards
            else -> R.id.navigation_home
        }
        bottom_navigation.selectedItemId = itemId
    }


    private fun selectTab(tab: SupportAppScreen) {
        currentTab = tab.screenKey
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            //            if (newFragment == null) {
//                add(R.id.tab_container, createTabFragment(tab), tab.screenKey)
//            }

            currentFragment?.let {
                detach(it)
//                hide(it)
//                it.userVisibleHint = false
            }
            newFragment?.let {
                attach(it)
//                show(it)
//                it.userVisibleHint = true
            }
        }.commitNow()
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun onBackPressed() {
        (currentTabFragment as? BackButtonListener)?.onBackPressed()
    }


}
