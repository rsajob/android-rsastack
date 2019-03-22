package com.rsastack.toothpick

import com.rsastack.domain.interactors.AuthData
import com.rsastack.domain.interactors.AuthDataPrefs

/**
 * Created by Aliaksandr Kazlou on 3/22/19.
 */
class NetworkModule : BaseNetworkModule() {
    init {
        bind(AuthData::class.java).to(AuthDataPrefs::class.java).singletonInScope()
    }
}