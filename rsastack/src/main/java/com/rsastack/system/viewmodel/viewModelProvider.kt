package com.myapp.ui.mvvmtest

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rsastack.system.toothpick.toothpickCreateInstance
import toothpick.Toothpick
import toothpick.config.Module

fun provideFactory(scopeName:String, cls:Class<*>):ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(cls))
            return Toothpick.openScope(scopeName).getInstance(cls) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

fun provideFactory(scopeName:String, cls:Class<*>, bindings: Module.() -> Unit = {}):ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(cls))
            return toothpickCreateInstance(scopeName, cls, bindings) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

fun <T : ViewModel> Fragment.provideViewModel(scopeName:String, cls:Class<T>):T
{
    val viewModelFactory = provideFactory(scopeName, cls)
    return ViewModelProvider(this, viewModelFactory).get(cls)
}

fun <T : ViewModel> Fragment.provideViewModel(scopeName:String, cls:Class<T>, bindings: Module.() -> Unit = {}):T
{
    val viewModelFactory = provideFactory(scopeName, cls, bindings)
    return ViewModelProvider(this, viewModelFactory).get(cls)
}