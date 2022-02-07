package com.myapp.ui

import android.os.Bundle
import androidx.lifecycle.*
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import moxy.MvpView
import com.myapp.toothpick.DI
import com.github.terrakok.cicerone.Router
import com.myapp.toothpick.networkModule
import com.rsastack.system.mvvm.MvvmSingleAppActivity
import com.rsastack.system.mvvm.provideViewModel
import com.rsastack.system.toothpick.flowModule
import com.rsastack.system.toothpick.initDynamicUiScope
import com.rsastack.system.toothpick.initUiScope
import com.rsastack.system.utils.debug
import toothpick.Toothpick
import toothpick.ktp.binding.module
import javax.inject.Inject

class AppActivity: MvvmSingleAppActivity()
{
    private lateinit var viewModel:AppViewModel

    private val scopeName:String by initUiScope("AppActivity") { realScopeName ->
        debug("Toothpick: initUiScope(\"AppActivity\")")
        DI.APP_ACTIVITY_SCOPE = realScopeName // Save the dynamic scope name
        val scope = Toothpick.openScopes(DI.APP_SCOPE, DI.APP_ACTIVITY_SCOPE)
        scope.installModules(
            module {
                // Navigation
                val cicerone = Cicerone.create()
                bind(Router::class.java).toInstance(cicerone.router)
                bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())
            }
        )

        val serverScope = Toothpick.openScopes(DI.APP_ACTIVITY_SCOPE, DI.SERVER_SCOPE)
        serverScope.installModules(
            networkModule()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this@AppActivity, Toothpick.openScope(scopeName))
        super.onCreate(savedInstanceState)

        viewModel = provideViewModel(DI.APP_ACTIVITY_SCOPE, AppViewModel::class.java)

        setContentView(layoutRes)

        if (supportFragmentManager.fragments.isEmpty())
            viewModel.coldStart()
    }
}

class AppViewModel @Inject constructor(
    private val router: Router
) : ViewModel()
{
    fun coldStart() {
        router.newRootScreen(Screens.TopFlow())
    }
}