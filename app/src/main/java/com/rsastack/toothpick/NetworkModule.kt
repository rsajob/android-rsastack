package com.rsastack.toothpick

import com.rsastack.domain.interactors.AuthData
import com.rsastack.domain.interactors.AuthDataPrefs
import okhttp3.OkHttpClient
import com.rsastack.toothpick.providers.OkHttpProvider
import toothpick.config.Module

class NetworkModule() : Module() {
    init {
        bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).providesSingletonInScope()

        bind(AuthData::class.java).to(AuthDataPrefs::class.java).singletonInScope()

    }
}

