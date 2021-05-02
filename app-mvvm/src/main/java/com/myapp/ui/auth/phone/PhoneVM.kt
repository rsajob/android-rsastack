package com.myapp.ui.auth.phone

import androidx.lifecycle.ViewModel
import moxy.InjectViewState
import moxy.viewstate.strategy.StateStrategyType
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import com.myapp.domain.interactors.AuthInteractor
import kotlinx.coroutines.*
import com.rsastack.system.navigation.FlowRouter
import com.rsastack.system.utils.debug
import com.rsastack.system.utils.warn
import com.myapp.ui.Screens
import com.myapp.ui.splash.State
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class PhoneVM @Inject constructor(
    private val router: FlowRouter,
    private val authInteractor:AuthInteractor
) : ViewModel() {

    sealed class State{
        object Idle : State()
        object Progress : State()
        class Error(val message:String): State()
    }

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private var disposable:Disposable? = null

    fun onBackPressed() {
        router.exit()
    }

    fun pressNext(phone:String)
    {
        disposable?.dispose()

        _state.tryEmit(State.Progress)
        val phoneFix = phone.replace("+7", "")

        disposable = authInteractor.requestSmsCode(phoneFix)
            .subscribe(
                {
                    router.navigateTo(Screens.AuthSms())
                },
                {
                    val msg = it.message ?: it.javaClass.simpleName
                    _state.tryEmit(State.Error(msg))
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}
