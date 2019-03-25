package com.rsastack.system.toothpick

import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module

fun Scope.inject(any: Any) = Toothpick.inject(any, this)

fun Scope.module(func: Module.() -> Unit): Scope {
    installModules(com.rsastack.system.toothpick.module { func(this) })
    return this
}
