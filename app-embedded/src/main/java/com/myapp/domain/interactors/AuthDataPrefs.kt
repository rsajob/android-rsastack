package com.myapp.domain.interactors

import android.content.Context
import com.rsastack.system.sharedprefs.PrefDelegate
import javax.inject.Inject

open class AuthDataImpl(
    override var phone : String? = null,
    override var clientId : String? = null,
    override var accessToken : String? = null,
    override var refreshToken : String? = null
) : AuthData
{
    override fun clear() {
        phone = null
        clientId = null
        accessToken = null
        refreshToken = null
    }

    override fun copyFrom(newData: AuthData) {
        phone = newData.phone
        clientId = newData.clientId
        accessToken = newData.accessToken
        refreshToken = newData.refreshToken
    }

}

class AuthDataPrefs @Inject constructor(context: Context) : AuthDataImpl()
{
    companion object {
        private const val PREFS_NAME = "auth"
    }

    override var phone:String? by PrefDelegate(context, null, PREFS_NAME)
    override var clientId:String? by PrefDelegate(context, null, PREFS_NAME)
    override var accessToken:String? by PrefDelegate(context, null, PREFS_NAME)
    override var refreshToken:String? by PrefDelegate(context, null, PREFS_NAME)
}