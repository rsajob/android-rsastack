package com.myapp.ui.auth.sms

import androidx.lifecycle.ViewModel
import com.myapp.domain.interactors.AuthInteractor
import com.myapp.ui.Screens
import com.rsastack.system.navigation.FlowRouter
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SmsVM @Inject constructor(
    private val router: FlowRouter,
    private val authInteractor: AuthInteractor
) : ViewModel() {

    sealed class State{
        object Idle : State()
        object Progress : State()
        class Error(val message:String): State()
    }

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private var disposable: Disposable? = null

    fun onBackPressed() {
        router.exit()
    }

    fun pressNext(sms:String)
    {
        disposable?.dispose()

        _state.tryEmit(State.Progress)

        disposable = authInteractor.login(sms)
            .subscribe(
                {
                    router.newRootFlow(Screens.MainTabs())
                },
                {
                    val msg = it.message ?: it.javaClass.simpleName
                    _state.tryEmit(State.Error(msg))
                }
            )
    }

    fun pressCancel() {
        disposable?.dispose()
        router.exit()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}
