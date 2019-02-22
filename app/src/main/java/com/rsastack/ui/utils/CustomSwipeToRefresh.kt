package com.rsastack.ui.utils

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

/**
 * Created by Roman Savelev (aka @rsa) on 11/18/18.
 */
class CustomSwipeToRefresh(context: Context, attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {

    private val mTouchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var mPrevX: Float = 0.toFloat()

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> mPrevX = MotionEvent.obtain(event).x

            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = Math.abs(eventX - mPrevX)

                if (xDiff > mTouchSlop) {
                    return false
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }
}