package com.rsastack.system.toothpick

import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module

fun Scope.inject(any: Any) = Toothpick.inject(any, this)

fun Scope.module(func: Module.() -> Unit): Scope {
    installModules(com.rsastack.system.toothpick.module { func(this) })
    return this
}

fun <T> toothpickCreateInstance(
        scopeName: Any,
        clazz: Class<T>,
        bindings: Module.() -> Unit = {}
): T
{
    val tmpScope = "tmpScope"
    return Toothpick.openScopes(scopeName, tmpScope)
            .module { bindings() }
            .getInstance(clazz)
            .also { Toothpick.closeScope(tmpScope) }
}
