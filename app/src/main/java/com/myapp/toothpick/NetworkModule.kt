package com.myapp.toothpick

import com.myapp.domain.interactors.AuthData
import com.myapp.domain.interactors.AuthDataPrefs
import com.myapp.toothpick.providers.OkHttpProvider
import okhttp3.OkHttpClient
import toothpick.ktp.binding.module

fun networkModule() = module {
    bind(AuthData::class.java).to(AuthDataPrefs::class.java).singleton()
    bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).providesSingleton()
}