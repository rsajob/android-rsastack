package com.myapp.testing

import android.preference.PreferenceManager
import com.myapp.App
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