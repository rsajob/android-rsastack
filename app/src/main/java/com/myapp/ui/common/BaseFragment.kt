package com.myapp.ui.common

import android.os.Bundle
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import com.rsastack.system.moxy.MvpAppCompatFragment
import com.rsastack.system.navigation.BackButtonListener

abstract class BaseFragment : MvpAppCompatFragment, BackButtonListener {

    constructor() : super()

    @ContentView
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { restoreState(it) }
    }

    protected open fun restoreState(state: Bundle) {}

    override fun onBackPressed() {}
}