package com.rsastack.toothpick

import com.rsastack.domain.interactors.AuthData
import com.rsastack.domain.interactors.AuthDataPrefs
import okhttp3.OkHttpClient
import toothpick.config.Module

/**
 * Created by Aliaksandr Kazlou on 3/22/19.
 */
class NetworkModule : Module() {
    init {
        bind(AuthData::class.java).to(AuthDataPrefs::class.java).singletonInScope()
        bind(OkHttpClient::class.java).toProvider(com.rsastack.toothpick.providers.OkHttpProvider::class.java).providesSingletonInScope()
    }
}