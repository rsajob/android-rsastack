package com.rsastack.domain.interactors

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authData: AuthData
)
{
    fun requestSmsCode(phone:String): Completable
    {
        authData.phone = phone
        return Completable.complete()
            .delaySubscription(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(smsCode:String): Completable
    {
        authData.clientId = "clientId"
        authData.accessToken = "accessToken"
        authData.refreshToken = "refreshToken"
        return Completable.complete()
            .delaySubscription(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun logout() {
        authData.clear()
    }

    fun isLoggedIn(): Boolean {
        return authData.phone != null &&
                authData.clientId != null &&
                authData.accessToken != null &&
                authData.refreshToken != null
    }


}
