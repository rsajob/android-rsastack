package com.rsastack.toothpick.providers

import com.rsastack.BuildConfig
import com.rsastack.system.network.CurlLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class OkHttpProvider @Inject constructor() : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.readTimeout(20, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
            httpClientBuilder.addNetworkInterceptor(CurlLoggingInterceptor())
        }

        return httpClientBuilder.build()
    }
}