package com.rsastack.system.mvp

import android.os.Bundle
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import com.rsastack.system.base.RealDestroyFragment
import moxy.MvpDelegate
import moxy.MvpDelegateHolder

open class MvpFragment : RealDestroyFragment, MvpDelegateHolder {
    private var flagIsStateSaved = false
    private var mvpDelegate: MvpDelegate<out MvpFragment>? = null

    constructor() : super()

    @ContentView
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        flagIsStateSaved = false
        getMvpDelegate().onAttach()
    }

    override fun onResume() {
        super.onResume()
        flagIsStateSaved = false
        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        flagIsStateSaved = true
        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate().onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpDelegate().onDetach()
        getMvpDelegate().onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        //We leave the screen and respectively all fragments will be destroyed
        if (requireActivity().isFinishing) {
            getMvpDelegate().onDestroy()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (flagIsStateSaved) {
            flagIsStateSaved = false
            return
        }
        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }
        if (isRemoving || anyParentIsRemoving) {
            getMvpDelegate().onDestroy()
        }
    }

    /**
     * @return The [MvpDelegate] being used by this Fragment.
     */
    override fun getMvpDelegate(): MvpDelegate<*> {
        if (mvpDelegate == null) {
            mvpDelegate = MvpDelegate(this)
        }
        return mvpDelegate!!
    }
}