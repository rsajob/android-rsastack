package com.rsastack.system.moxy

import android.os.Bundle
import moxy.MvpDelegate

class MvpDelegateObservable<Delegated>(delegated:Delegated) : MvpDelegate<Delegated>(delegated)
{
    interface LifecycleObserver
    {
        fun onCreate(){}
        fun onCreate(bundle: Bundle?){}
        fun onAttach(){}
        fun onDetach(){}
        fun onDestroyView(){}
        fun onDestroy(){}
        fun onSaveInstanceState(){}
        fun onSaveInstanceState(outState: Bundle?){}
    }

    var lifecycleObserver: LifecycleObserver? = null

    override fun onCreate(){
        super.onCreate()
        lifecycleObserver?.onCreate()
    }

    override fun onCreate(bundle: Bundle?){
        super.onCreate(bundle)
        lifecycleObserver?.onCreate(bundle)
    }

    override fun onAttach(){
        super.onAttach()
        lifecycleObserver?.onAttach()
    }

    override fun onSaveInstanceState()
    {
        super.onSaveInstanceState()
        lifecycleObserver?.onSaveInstanceState()
    }

    override fun onSaveInstanceState(outState: Bundle?)
    {
        super.onSaveInstanceState(outState)
        lifecycleObserver?.onSaveInstanceState(outState)
    }

    override fun onDetach(){
        super.onDetach()
        lifecycleObserver?.onDetach()
    }

    override fun onDestroyView(){
        super.onDestroyView()
        lifecycleObserver?.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleObserver?.onDestroy()
        lifecycleObserver = null
    }

}