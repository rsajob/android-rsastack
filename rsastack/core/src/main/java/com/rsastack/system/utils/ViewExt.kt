package com.rsastack.system.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}


fun Fragment.hideKeyboard(v: View? = null) {
    activity?.hideKeyboard(v)
}

@Suppress("DEPRECATION")
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

fun ViewGroup.inflate(layoutResId: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutResId, root, attachToRoot)

/**
 * Fixes a problem with fitsSystemWindows where only the first view in viewHierarchy has a chance
 * to offset itself away from Status and Navigation bars. Typically when a view is defined
 * to fitsSystemWindows, it consumes those offsets, leaving no offsets for other views.
 * Listen for dispatch of WindowInsets and redispatch the offsets to all children. Even if the first
 * child consumes offsets, other children get the original offsets and can react accordingly as well.
 *
 * https://medium.com/androiddevelopers/windows-insets-fragment-transitions-9024b239a436
 */
fun ViewGroup.redispatchWindowInsetsToAllChildren() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        var consumed = false
        val parent = view as ViewGroup

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            // Dispatch the insets to the child
            val childResult = ViewCompat.dispatchApplyWindowInsets(child, insets)
            // If the child consumed the insets, record it
            if (childResult.isConsumed) {
                consumed = true
            }
        }

        // If any of the children consumed the insets, return an appropriate value
        if (consumed) insets.consumeSystemWindowInsets() else insets
    }
}