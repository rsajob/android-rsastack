package com.rsastack.system.toothpick

import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.module

fun <T> toothpickCreateInstance(
        scopeName: Any,
        clazz: Class<T>,
        bindings: Module.() -> Unit = {}
): T
{
    val tmpScope = "tmpScope"
    return Toothpick.openScopes(scopeName, tmpScope)
            .installModules( module { bindings() } )
            .getInstance(clazz)
            .also { Toothpick.closeScope(tmpScope) }
}
