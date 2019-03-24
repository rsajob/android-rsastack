package com.rsastack.system.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}


fun Fragment.hideKeyboard(v: View? = null) {
    activity?.hideKeyboard(v)
}

fun Fragment.setupKeyboardModeResize() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

fun Fragment.setupKeyboardModePan() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}

fun Activity.hideKeyboard(v: View? = null) {
    val view = v ?: this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun ViewGroup.inflate(layoutResId: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutResId, root, attachToRoot)