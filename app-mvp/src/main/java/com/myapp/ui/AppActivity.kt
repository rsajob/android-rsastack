package com.myapp.ui

import android.os.Bundle
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import com.myapp.toothpick.DI
import com.rsastack.system.singleactivity.BaseSingleAppActivity
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity: BaseSingleAppActivity(), MvpView
{
    @InjectPresenter
    lateinit var presenter: AppPresenter

    @ProvidePresenter
    fun providePresenter() = Toothpick.openScope(DI.APP_SCOPE).getInstance(AppPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this@AppActivity, Toothpick.openScope(DI.APP_SCOPE))

        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        if (supportFragmentManager.fragments.isEmpty())
            presenter.coldStart()
    }
}

class AppPresenter @Inject constructor(
    private val router: Router
) : MvpPresenter<MvpView>()
{
    fun coldStart() {
        router.newRootScreen(Screens.TopFlow())
    }
}