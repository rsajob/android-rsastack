package com.myapp.ui.auth.phone

import moxy.InjectViewState
import moxy.viewstate.strategy.StateStrategyType
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import com.myapp.domain.interactors.AuthInteractor
import com.rsastack.system.navigation.FlowRouter
import com.myapp.ui.Screens
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@StateStrategyType(AddToEndSingleStrategy::class)
interface PhoneView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(msg:String)

    fun showProgress(shown:Boolean)
}

@InjectViewState
class PhonePresenter @Inject constructor(
    private val router: FlowRouter,
    private val authInteractor:AuthInteractor
) : MvpPresenter<PhoneView>() {

    private var disposable:Disposable? = null

    fun onBackPressed() {
        router.exit()
    }

    fun pressNext(phone:String)
    {
        disposable?.dispose()

        viewState.showProgress(true)
        val phoneFix = phone.replace("+7", "")

        disposable = authInteractor.requestSmsCode(phoneFix)
            .doFinally { viewState.showProgress(false) }
            .subscribe(
                {
                    router.navigateTo(Screens.AuthSms())
                },
                {
                    val msg = it.message ?: it.javaClass.simpleName
                    viewState.showMessage(msg)
                }
            )
    }

    fun pressCancel()
    {
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
