package com.rsastack.toothpick.provider

import com.rsastack.BuildConfig
import com.rsastack.network.interceptors.CurlLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class OkHttpProvider @Inject constructor(private val timeout: Timeout) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.readTimeout(timeout.value, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(timeout.value, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
            httpClientBuilder.addNetworkInterceptor(CurlLoggingInterceptor())
        }

        return httpClientBuilder.build()
    }
}