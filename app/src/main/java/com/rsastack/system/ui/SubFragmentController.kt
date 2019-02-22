package com.rsastack.system.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


class SubFragmentController(
        private val subContainerId: Int,
        private val subTags: List<String>,
        private val subFragmentsFactory: (String)-> Fragment,
        private val firstSubIndex: Int = 0
    )
{
    lateinit var fm: FragmentManager
    lateinit var subFragments: Map<String, Fragment>

    /**
     * Must be called from fragment or activity onCreate()
     */
    fun onCreate(fm: FragmentManager, savedInstanceState: Bundle?, initNow:Boolean = true) {
        this.fm = fm
        if (savedInstanceState == null) {
            if (initNow)
                init()
        } else {
            subFragments = findFragments()
        }
    }

    fun init()
    {
        subFragments = subTags.map { tag -> tag to subFragmentsFactory(tag)}.toMap()
        initTabs()
    }

    private fun initTabs() {
        subFragments.forEach { (tabKey, fragment) ->
            fm.beginTransaction()
                    .add(subContainerId, fragment, tabKey)
                    .detach(fragment)
                    .commitNow()
        }
        switchTabTo(firstSubIndex)
    }

    private fun findFragments(): Map<String, Fragment> {
        return subTags.mapIndexed { index, tag -> tag to (fm.findFragmentByTag(subTags[index]) as Fragment) }.toMap()
    }

    fun switchTabTo(subFragmentTag: String) {

        val transaction = fm.beginTransaction()

        subFragments.forEach { (tabKey, fragment) ->
            if (subFragmentTag != tabKey)
                transaction.detach(fragment)
        }

        subFragments[subFragmentTag]?.let { transaction.attach(it) }
        transaction.commit()
    }

    fun switchTabTo(subFragmentPos: Int) = switchTabTo(subTags[subFragmentPos])

}