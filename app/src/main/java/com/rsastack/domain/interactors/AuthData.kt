package com.rsastack.domain.interactors


interface AuthData {
    var phone: String?
    var clientId: String?
    var accessToken: String?
    var refreshToken: String?
    fun clear()
    fun copyFrom(newData: AuthData)
}
