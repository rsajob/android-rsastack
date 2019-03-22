package com.rsastack.toothpick

import com.rsastack.toothpick.provider.Timeout
import okhttp3.OkHttpClient
import toothpick.config.Module

open class BaseNetworkModule : Module() {
    init {
        this.bind(Timeout::class.java).toInstance(Timeout(20))
        this.bind(OkHttpClient::class.java).toProvider(com.rsastack.toothpick.provider.OkHttpProvider::class.java).providesSingletonInScope()
    }
}

