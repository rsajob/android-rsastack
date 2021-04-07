package com.myapp.ui.maintabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.myapp.R
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.utils.debug
import com.rsastack.system.utils.redispatchWindowInsetsToAllChildren
import com.myapp.toothpick.DI
import com.myapp.ui.Screens
import com.myapp.ui.SupportAppTabScreen
import com.myapp.ui.common.BaseFragment
import com.rsastack.system.utils.setupKeyboardModePan
import kotlinx.android.synthetic.main.fragment_main.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import toothpick.Toothpick

private const val CURRENT_TAB = "current_tab"

class MainTabsFragment : BaseFragment(), MainTabsView {

    override val layoutRes = R.layout.fragment_main
    private val tabs = listOf(Screens.TabHome, Screens.TabContacts)

    private val currentTabFragment: Fragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } // Обязательно именно так!!! а не findFragmentById(R.id.tab_container)

    @InjectPresenter
    lateinit var presenter: MainTabsPresenter

    @ProvidePresenter
    fun providePresenter(): MainTabsPresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(MainTabsPresenter::class.java)

    private var currentTab: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupKeyboardModePan()
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        currentTab?.let { outState.putString(CURRENT_TAB, it) }
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null)
            currentTab = savedInstanceState.getString(CURRENT_TAB, null)

        setupWindowInsets()
        initBottomNavigation()
    }

    private fun setupWindowInsets(){
        tab_container.redispatchWindowInsetsToAllChildren()
    }

    private fun initBottomNavigation() {
        // Default Tab
        if (currentTab == null)
            currentTab = Screens.TabHome.screenKey

        currentTab?.let { selectTabByScreenKey(it) }

        // bottom_navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED

        // Listener выставляем обязательно после установки первого таба, иначе selectTab будет вызван 2 раза
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            tabs.find { it.navigationIdRes == item.itemId }?.let { selectTab(it) }
            true
        }
    }

    private fun selectTabByScreenKey(screenKey: String) {
        tabs.find { it.screenKey == screenKey }?.let { screen ->
            selectTab(screen)
            bottom_navigation.selectedItemId = screen.navigationIdRes
        }
    }

    private fun selectTab(tab: SupportAppTabScreen) {
        currentTab = tab.screenKey
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)
        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {

            currentFragment?.let { detach(it) }

            if (newFragment != null)
                attach(newFragment)
            else
                createTabFragment(tab)?. let { add(R.id.tab_container, it, tab.screenKey) }

        }.commitNow()
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun onBackPressed() { (currentTabFragment as? BackButtonListener)?.onBackPressed() }
}
