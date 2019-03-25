package com.rsastack.toothpick

import com.rsastack.domain.interactors.AuthData
import com.rsastack.domain.interactors.AuthDataPrefs
import com.rsastack.system.toothpick.module
import okhttp3.OkHttpClient
import toothpick.config.Module

fun networkModule() = module {
    bind(AuthData::class.java).to(AuthDataPrefs::class.java).singletonInScope()
    bind(OkHttpClient::class.java).toProvider(com.rsastack.toothpick.providers.OkHttpProvider::class.java).providesSingletonInScope()
}