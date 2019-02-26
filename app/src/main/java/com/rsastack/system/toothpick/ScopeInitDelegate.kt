package com.rsastack.system.toothpick

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.rsastack.system.utils.verbose
import toothpick.Toothpick
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
 * Сохранение имени скоупа в аргументы фрагмента ещё нужно для динамических скоупов, когда мы генерируем имя скойпа при
 * открытии фрагмента первый раз.
 *
 * @author Roman Savelev (aka fantom and rsajob). Date: 23.10.18
 */
class ScopeInitDelegate(
    private val initScopeName: String,
    private val fragment: Fragment,
    private val initScope: ((String) -> Unit)? = null
){
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String
    {
        var realScopeName = fragment.arguments?.getString(ARG_SCOPE_NAME)
        if (realScopeName == null) {
            realScopeName = initScopeName
            // Save scope name to fragment arguments
            fragment.arguments = (fragment.arguments ?: Bundle()).apply { putString(ARG_SCOPE_NAME, realScopeName) }
        }

        if (!Toothpick.isScopeOpen(realScopeName)) {
            Log.v(LOG_TAG, "Init new UI scope: $realScopeName")
            initScope?.invoke(realScopeName)
        }

       return realScopeName
    }

    companion object {
        const val LOG_TAG = "Toothpick"
        const val ARG_SCOPE_NAME = "arg_scope_name"
    }

}

fun uniqueScopeName(baseScopeName:String):String = "${baseScopeName}_${System.currentTimeMillis()}"

fun Fragment.initScope(scopeName:String, initScope: ((String) -> Unit)?) =
    ScopeInitDelegate(scopeName, this, initScope)

fun Fragment.initDynamicScope(baseScopeName:String, initScope: ((String) -> Unit)?) =
    ScopeInitDelegate(uniqueScopeName(baseScopeName), this, initScope)

fun Fragment.initDynamicScope(initScope: ((String) -> Unit)?) =
    ScopeInitDelegate(uniqueScopeName(this::class.java.simpleName),this,initScope)
