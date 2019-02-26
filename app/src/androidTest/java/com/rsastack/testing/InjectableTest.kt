package com.rsastack.testing

import android.preference.PreferenceManager
import com.rsastack.App
import org.junit.Before


interface InjectableTest {

    val app: App

    @Before
    fun resetSharedPreferences() {
        PreferenceManager.getDefaultSharedPreferences(app).edit()
            .clear()
            .apply()
    }

}