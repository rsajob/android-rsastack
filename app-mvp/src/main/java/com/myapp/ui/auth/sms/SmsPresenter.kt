package com.myapp.ui.auth.sms

import moxy.InjectViewState
import moxy.viewstate.strategy.StateStrategyType
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import com.myapp.domain.interactors.AuthInteractor
import com.myapp.ui.Screens
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@StateStrategyType(AddToEndSingleStrategy::class)
interface SmsView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(msg:String)

    fun showProgress(shown:Boolean)
}

@InjectViewState
class SmsPresenter @Inject constructor(
    private val router: com.rsastack.system.navigation.FlowRouter,
    private val authInteractor: AuthInteractor
) : MvpPresenter<SmsView>() {

    private var disposable: Disposable? = null

    fun onBackPressed() {
        router.exit()
    }

    fun pressNext(sms:String)
    {
        disposable?.dispose()

        viewState.showProgress(true)


        disposable = authInteractor.login(sms)
            .doFinally { viewState.showProgress(false) }
            .subscribe(
                {
                    router.newRootFlow(Screens.MainTabs())
                },
                {
                    val msg = it.message ?: it.javaClass.simpleName
                    viewState.showMessage(msg)
                }
            )
    }

    fun pressCancel() {
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
