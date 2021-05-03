package com.rsastack.system.ui.behaviors

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat.setTranslationY
import com.google.android.material.floatingactionbutton.FloatingActionButton




class MoveUpwardBehavior @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : CoordinatorLayout.Behavior<View>(context, attrs) {



    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val translationY = Math.min(0f, dependency.translationY - dependency.height)
        child.translationY = translationY
        return true
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        super.onDependentViewRemoved(parent, child, dependency)
        val translationY = Math.min(0, parent.bottom - child.bottom).toFloat()
        child.translationY = translationY
    }

}