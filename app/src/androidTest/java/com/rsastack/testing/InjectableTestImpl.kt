package com.rsastack.testing

import androidx.test.platform.app.InstrumentationRegistry
import com.rsastack.App


class InjectableTestImpl : InjectableTest {
    override val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App
}