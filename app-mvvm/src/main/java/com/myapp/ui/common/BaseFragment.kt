package com.myapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsastack.system.moxy.MvpAppCompatFragment
import com.rsastack.system.navigation.BackButtonListener

abstract class BaseFragment : MvpAppCompatFragment(), BackButtonListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { restoreState(it) }
    }

    protected open fun restoreState(state: Bundle) {}

    override fun onBackPressed() {}
}