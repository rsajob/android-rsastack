package com.myapp.ui.common

import android.os.Bundle
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import com.rsastack.system.mvp.MvpFragment
import com.rsastack.system.navigation.BackButtonListener

abstract class BaseFragment : MvpFragment, BackButtonListener {

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