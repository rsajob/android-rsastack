package com.myapp.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.myapp.toothpick.DI
import com.rsastack.system.singleactivity.BaseSingleAppActivity
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity: com.rsastack.system.singleactivity.BaseSingleAppActivity(), MvpView
{
    @InjectPresenter
    lateinit var presenter: AppPresenter

    @ProvidePresenter
    fun providePresenter() =
        Toothpick.openScope(DI.APP_SCOPE)
            .getInstance(AppPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this@AppActivity, Toothpick.openScope(DI.APP_SCOPE))

        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        if (savedInstanceState == null) {
            presenter.coldStart()
        }
    }
}

class AppPresenter @Inject constructor(
    private val router: Router
) : MvpPresenter<MvpView>()
{
    fun coldStart() {
        router.newRootScreen(Screens.TopFlow)
    }
}