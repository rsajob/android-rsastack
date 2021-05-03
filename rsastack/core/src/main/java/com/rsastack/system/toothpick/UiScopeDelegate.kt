package com.rsastack.system.toothpick

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.rsastack.system.base.RealDestroyDialogFragment
import com.rsastack.system.base.RealDestroyFragment
import com.rsastack.system.base.RealDestroyListener
import com.rsastack.system.base.RealDestroyOwner
import toothpick.Toothpick
import kotlin.reflect.KProperty


/**
 * Класс-делегат предназначен для однократной инициализации UI скоупов Toothpick
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
 * Сохранение имени скоупа в аргументы фрагмента ещё нужно для динамических скоупов, когда мы генерируем имя скоупа при
 * открытии фрагмента первый раз.
 *
 * Класс жёстко связан с Moxy и Mvp фрагментами. UiScopeDelegate следит за жизненным циклом Mvp-делагата
 * и при onDestroy() закрывает UI-скоуп
 *
 * @author Roman Savelev (aka fantom and rsajob). Date: 23.10.18
 */

private const val LOG_TAG = "Toothpick"
private const val ARG_SCOPE_NAME = "arg_scope_name"

class UiScopeDelegate<F: Fragment>(
    private val initScopeName: String,
    private val fragment: F,
    realDestroyOwner: RealDestroyOwner,
    private val initScope: ((String) -> Unit)? = null
)
{
    private var realScopeName:String? = null

    init {
        realDestroyOwner.realDestroyListener = object : RealDestroyListener {
            override fun onRealDestroy() {
                closeScope()
            }
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String
    {
        var scopeName = fragment.arguments?.getString(ARG_SCOPE_NAME)
        if (scopeName == null) {
            scopeName = initScopeName
            // Save scope name to fragment arguments
            fragment.arguments = (fragment.arguments ?: Bundle()).apply { putString(ARG_SCOPE_NAME, scopeName) }
        }

        if (!Toothpick.isScopeOpen(scopeName)) {
            Log.v(LOG_TAG, "Init new UI scope: $scopeName")
            initScope?.invoke(scopeName)
        }

        this.realScopeName = scopeName
        return scopeName
    }


    fun closeScope()
    {
        if (realScopeName != null) {
            Log.i(LOG_TAG, "Close UI scope $realScopeName")
            Toothpick.closeScope(realScopeName)
        }
    }

}

fun Any.uniqueScopeName(baseScopeName:String):String = "${baseScopeName}_${System.currentTimeMillis()}_${hashCode()}"

fun RealDestroyFragment.initUiScope(scopeName:String, initScope: ((String) -> Unit)?) =
    UiScopeDelegate(scopeName, this,this, initScope)

fun RealDestroyFragment.initDynamicUiScope(baseScopeName:String, initScope: ((String) -> Unit)?) =
    UiScopeDelegate(
        uniqueScopeName(baseScopeName),
        this,
        this,
        initScope
    )

fun RealDestroyFragment.initDynamicUiScope(initScope: ((String) -> Unit)?) =
    UiScopeDelegate(
        uniqueScopeName(this::class.java.simpleName),
        this,
        this,
        initScope
    )

fun RealDestroyDialogFragment.initUiScope(scopeName:String, initScope: ((String) -> Unit)?) =
    UiScopeDelegate(scopeName, this, this, initScope)

fun RealDestroyDialogFragment.initDynamicUiScope(baseScopeName:String, initScope: ((String) -> Unit)?) =
    UiScopeDelegate(
        uniqueScopeName(baseScopeName),
        this,
        this,
        initScope
    )

fun RealDestroyDialogFragment.initDynamicUiScope(initScope: ((String) -> Unit)?) =
    UiScopeDelegate(
        uniqueScopeName(this::class.java.simpleName),
        this,
        this,
        initScope
    )
