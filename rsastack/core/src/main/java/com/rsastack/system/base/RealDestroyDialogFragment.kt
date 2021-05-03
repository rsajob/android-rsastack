package com.rsastack.system.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

open class RealDestroyDialogFragment : AppCompatDialogFragment(), RealDestroyOwner {

    override var realDestroyListener: RealDestroyListener? = null

    private var mIsStateSaved:Boolean = false

    override fun onStart() {
        super.onStart()
        mIsStateSaved = false
    }

    override fun onResume() {
        super.onResume()
        mIsStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mIsStateSaved = true
    }

    override fun onDestroy() {
        super.onDestroy()

        //We leave the screen and respectively all fragments will be destroyed
        if (requireActivity().isFinishing) {
            onRealDestroy()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (mIsStateSaved) {
            mIsStateSaved = false
            return
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            onRealDestroy()
        }

    }

    open fun onRealDestroy()
    {
        realDestroyListener?.onRealDestroy()
    }

}