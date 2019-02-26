package com.rsastack

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rsastack.testing.InjectableTest
import com.rsastack.testing.InjectableTestImpl
import com.rsastack.system.utils.debug
import com.rsastack.toothpick.DI
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import toothpick.Scope
import toothpick.Toothpick

/**
 * Created by Roman Savelev (aka @rsa) on 02/25/19.
 */
@RunWith(AndroidJUnit4::class)
class ExampleTest : InjectableTest by InjectableTestImpl() {
    lateinit var scope: Scope

    @Before
    fun setup() {
        Toothpick.reset()
        app.initAppScope(Toothpick.openScope(DI.APP_SCOPE))
        scope = Toothpick.openScope(DI.SERVER_SCOPE)
    }

    @Test
    fun test() {
//        val api = scope.getInstance(ExampleApi::class.java)
//        val resp = api.login("89600343234", "123").blockingGet()
        debug("Test ok")
    }
}