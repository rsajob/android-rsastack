package com.rsastack.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.rsastack.system.navigation.BackButtonListener

abstract class BaseFragment : MvpAppCompatFragment(), BackButtonListener {

    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { restoreState(it) }
    }

    protected open fun restoreState(state: Bundle) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    override fun onBackPressed() {}
}