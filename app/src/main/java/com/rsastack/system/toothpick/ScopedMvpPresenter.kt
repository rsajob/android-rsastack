package com.rsastack.system.toothpick

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

/**
 * Created by Roman Savelev (aka @rsa) on 17.12.2018
 */
open class ScopedMvpPresenter<T : MvpView> : MvpPresenter<T>()
{
    @Inject
    lateinit var scope: Scope

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(scope.name)
    }
}