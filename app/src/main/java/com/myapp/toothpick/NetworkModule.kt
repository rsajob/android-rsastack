package com.myapp.toothpick

import com.myapp.domain.interactors.AuthData
import com.myapp.domain.interactors.AuthDataPrefs
import com.myapp.toothpick.providers.OkHttpProvider
import com.rsastack.system.toothpick.module
import okhttp3.OkHttpClient

fun networkModule() = module {
    bind(AuthData::class.java).to(AuthDataPrefs::class.java).singleton()
    bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).providesSingleton()
}