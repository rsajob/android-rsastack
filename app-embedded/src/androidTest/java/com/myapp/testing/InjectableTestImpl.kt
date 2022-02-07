package com.myapp.testing

import androidx.test.platform.app.InstrumentationRegistry
import com.myapp.App


class InjectableTestImpl : InjectableTest {
    override val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App
}