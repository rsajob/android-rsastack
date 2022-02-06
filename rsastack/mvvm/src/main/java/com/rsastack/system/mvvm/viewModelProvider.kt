package com.rsastack.system.mvvm

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rsastack.system.toothpick.toothpickCreateInstance
import toothpick.Toothpick
import toothpick.config.Module

@Suppress("UNCHECKED_CAST")
fun provideFactory(scopeName:String, cls:Class<*>):ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(cls))
            return Toothpick.openScope(scopeName).getInstance(cls) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Suppress("UNCHECKED_CAST")
fun provideFactory(scopeName:String, cls:Class<*>, bindings: Module.() -> Unit = {}):ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(cls))
            return toothpickCreateInstance(scopeName, cls, bindings) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

inline fun <reified T : ViewModel> Fragment.provideViewModel(scopeName:String, noinline bindings: Module.() -> Unit = {}):T
{
    val viewModelFactory = provideFactory(scopeName, T::class.java, bindings)
    return ViewModelProvider(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> AppCompatActivity.provideViewModel(scopeName:String, noinline bindings: Module.() -> Unit = {}):T
{
    val viewModelFactory = provideFactory(scopeName, T::class.java, bindings)
    return ViewModelProvider(this, viewModelFactory)[T::class.java]
}

@Deprecated("Use inline method without cls:Class<T>", ReplaceWith("provideViewModel(scopeName)", "com.rsastack.system.mvvm"))
fun <T : ViewModel> Fragment.provideViewModel(scopeName:String, cls:Class<T>):T =
    ViewModelProvider(this, provideFactory(scopeName, cls))[cls]

@Deprecated("Use inline method without cls:Class<T>", ReplaceWith("provideViewModel(scopeName, bindings)", "com.rsastack.system.mvvm"))
fun <T : ViewModel> Fragment.provideViewModel(scopeName:String, cls:Class<T>, bindings: Module.() -> Unit = {}):T
{
    val viewModelFactory = provideFactory(scopeName, cls, bindings)
    return ViewModelProvider(this, viewModelFactory).get(cls)
}

@Deprecated("Use inline method without cls:Class<T>", ReplaceWith("provideViewModel(scopeName, bindings)", "com.rsastack.system.mvvm"))
fun <T : ViewModel> AppCompatActivity.provideViewModel(scopeName:String, cls:Class<T>):T
{
    val viewModelFactory = provideFactory(scopeName, cls)
    return ViewModelProvider(this, viewModelFactory).get(cls)
}

@Deprecated("Use inline method without cls:Class<T>", ReplaceWith("provideViewModel(scopeName, bindings)", "com.rsastack.system.mvvm"))
fun <T : ViewModel> AppCompatActivity.provideViewModel(scopeName:String, cls:Class<T>, bindings: Module.() -> Unit = {}):T
{
    val viewModelFactory = provideFactory(scopeName, cls, bindings)
    return ViewModelProvider(this, viewModelFactory).get(cls)
}