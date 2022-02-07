package com.myapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.myapp.toothpick.ARG_PARENT_SCOPE_NAME
import com.myapp.toothpick.DI
import com.myapp.toothpick.appModule
import com.myapp.toothpick.networkModule
import com.myapp.utils.withArguments
import com.rsastack.mvvm.R
import com.rsastack.system.base.RealDestroyFragment
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.navigation.setLaunchScreen
import com.rsastack.system.toothpick.initUiScope
import toothpick.Toothpick
import javax.inject.Inject

/**
 * Идея в следующем - фрагмент выступает как мини приложение и весь его стек технологий содержится
 * в нём (навигация, di и т.д.). При этом остальная часть приложения может быть написана на другом
 * стеке. Снаружи этот фаргмент может быть встроен в любое приложение как обычный фрагмент
 */
class FeatureAppFragment: RealDestroyFragment(), BackButtonListener {

    val layoutRes: Int = R.layout.layout_flow_container
    val containerId: Int = R.id.flow_container

    private val currentFragment
        get() = childFragmentManager.findFragmentById(containerId) as? BackButtonListener

    private val parentScope by lazy { requireArguments().getString(ARG_PARENT_SCOPE_NAME)
        ?: throw IllegalStateException("Need parent scope") }

    private val scopeName:String by initUiScope(DI.APP_SCOPE) { realScopeName ->
        val scope = Toothpick.openScopes(parentScope, realScopeName)
        scope.installModules(
            appModule(requireContext().applicationContext)
        )

        val serverScope = Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
        serverScope.installModules(
            networkModule()
        )

    }

    companion object {
        fun newInstance(parentScope:String):FeatureAppFragment =
            FeatureAppFragment().withArguments(ARG_PARENT_SCOPE_NAME to parentScope)
    }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected val navigator: Navigator by lazy {
        object : AppNavigator(requireActivity(), containerId, childFragmentManager) {
            override fun activityBack() {
                onExit()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutRes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(scopeName))

        super.onCreate(savedInstanceState)

        if (childFragmentManager.fragments.isEmpty())
            navigator.setLaunchScreen(Screens.TopFlow())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: onExit()
    }

    fun onExit() {
        (activity as? OnFragmentExit)?.onFragmentExit(this)
    }
}
