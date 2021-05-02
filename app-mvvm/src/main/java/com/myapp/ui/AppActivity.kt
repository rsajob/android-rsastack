package com.myapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import com.myapp.toothpick.DI
import com.rsastack.system.singleactivity.BaseSingleAppActivity
import com.github.terrakok.cicerone.Router
import com.rsastack.system.viewmodel.provideViewModel
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity: BaseSingleAppActivity(), MvpView
{
    private lateinit var viewModel:AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this@AppActivity, Toothpick.openScope(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)

        viewModel = provideViewModel(DI.APP_SCOPE, AppViewModel::class.java)

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