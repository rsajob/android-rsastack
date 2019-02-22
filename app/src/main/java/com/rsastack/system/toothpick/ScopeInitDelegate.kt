package com.rsastack.system.toothpick

import android.os.Bundle
import android.support.v4.app.Fragment
import toothpick.Toothpick
import toothpick.configuration.MultipleRootException
import kotlin.reflect.KProperty

/**
 * Класс-делегат предназначен для гарантии однократной инициализации скоупа Toothpick
 * при создании и восстановлени фрагментов.
 *
 * Проблема в следующем.
 * Скоуп должен быть инициализиорован в момент создания фрагмента, в методе Fragment.onCreate()
 * Потому что это является основной точкой входа в экран.
 *
 * Проблема в том, что у фрагментов этот метод вызывается всегда, не только при создании но и при восстановлении.
 * А нам надо проинициализировать скоуп только один раз.
 *
 * Стратегия следующая: добавляем в аргументы фрагмента параметр ARG_SCOPE_NAME
 * Если его ещё нет - это значит, что фрагмент создаётся первый раз, и нужно инициализировать скоуп
 * и тут же сохраняем ARG_SCOPE_NAME в аргументы фрагмента
 *
 * Если ARG_SCOPE_NAME уже есть - это значит, что происходит восстановление фрагмента,
 * например была смена конфигурации (переворот экрана). В этом случае скоуп уже проинициализирован
 * и ничего делать не надо.
 *
 * Но при убийстве процесса системой, при восстановлении фрагментов, аргумент ARG_SCOPE_NAME будет присутствовать,
 * но скоуп не будет инициализирован, поэтому если аргумент установлен, мы проверяем не открыт ли скоуп, если не
 * открыт то инициализируем.
 *
 * Есть только один костыльный вариант проверить, открыт ли уже скоуп в Toothpick или нет
 *
 * Это явно открыть скоуп Toothpick.openScope(scopeName) и проверить есть ли у него родитель.
 * Если родителя нет (или получаем MultipleRootException) то это значит, что скоуп не инициализирован и инициализируем
 * его.
 *
 * Сохранение имени скоупа в аргументы фрагмента ещё нужно для динамических скоупов, когда мы генерируем имя скойпа при
 * открытии фрагмента первый раз.
 *
 * @author Roman Savelev (aka fantom and rsajob). Date: 23.10.18
 */
class ScopeInitDelegate(
    val initScopeName: String,
    private val fragment:Fragment,
    private val initScope: ((String) -> Unit)? = null
){
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String
    {
        var realScopeName = fragment.arguments?.getString(ARG_SCOPE_NAME)
        if (realScopeName == null) {

            realScopeName = initScopeName

            // Save scope name to fragment arguments
            fragment.arguments = (fragment.arguments ?: Bundle()).apply { putString(ARG_SCOPE_NAME, realScopeName) }

//            verbose("int scope: $scopeName")
            initScope?.invoke(realScopeName)

        }else
        if (!isScopeOpened(realScopeName)) {
//            verbose("int scope: $scopeName")
            initScope?.invoke(realScopeName)
        }

       return realScopeName
    }

    /**
     * Проверяет открыт ли скоуп
     */
    private fun isScopeOpened(scopeName:String) =
        try {
            val isRootScope = Toothpick.openScope(scopeName).parentScope == null
            if (isRootScope) {
                Toothpick.closeScope(scopeName)
                false
            }else
                true
        } catch (e: MultipleRootException) {
            false
        }


    companion object {
        const val ARG_SCOPE_NAME = "arg_scope_name"
    }

}

fun uniqueScopeName(baseScopeName:String):String = "${baseScopeName}_${System.currentTimeMillis()}"

fun Fragment.initScope(scopeName:String, initScope: ((String) -> Unit)?) =
    ScopeInitDelegate(scopeName, this, initScope)

fun Fragment.initDynamicScope(baseScopeName:String, initScope: ((String) -> Unit)?) =
    ScopeInitDelegate(
        uniqueScopeName(
            baseScopeName
        ), this, initScope
    )

fun Fragment.initDynamicScope(initScope: ((String) -> Unit)?) =
    ScopeInitDelegate(
        uniqueScopeName(this::class.java.simpleName),
        this,
        initScope
    )
