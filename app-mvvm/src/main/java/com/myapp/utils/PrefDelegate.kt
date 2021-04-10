package com.myapp.utils

import android.content.Context
import android.preference.PreferenceManager
import com.rsastack.system.utils.err
import java.io.IOException
import kotlin.reflect.KProperty

class PrefDelegate<T>(
    private val context: Context,
    private val default: T,
    private val prefsName: String? = null,
    private val propName: String? = null,
    private var fromString: ((String)->T)? = null,
    private var toString: ((T)->String)? = null
)
{
    private fun getSharedPreferences()
            = if (prefsName != null) context.getSharedPreferences(prefsName, Context.MODE_PRIVATE) else PreferenceManager.getDefaultSharedPreferences(context)

    data class CacheValue<T>(var value:T)

    private var cachedValue: CacheValue<T>? = null

    private fun saveData(key:String, value:T){
        try {
            val edit = getSharedPreferences().edit()

            if (value == null)
                edit.remove(key)
            else
                when (value)
                {
                    is String -> edit.putString(key, value)
                    is Int -> edit.putInt(key, value)
                    is Float -> edit.putFloat(key, value)
                    is Boolean -> edit.putBoolean(key, value)
                    is Long -> edit.putLong(key, value)
                    else -> {

                        if (toString != null)
                            edit.putString(key, toString?.invoke(value))
                        else
                            throw IOException("Unsupported type")
                    }
                }

            if (cachedValue == null)
                cachedValue = CacheValue(value)
            else
                cachedValue!!.value = value

            edit.apply()

        } catch (e: Exception) {
            err("Сan't save data to SharedPreferences: $key -> $value", e)
        }
    }

    private fun readData(key:String):T{

        if (cachedValue != null)
            return cachedValue!!.value

        try {
            val prefs = getSharedPreferences()

            when (default) {
                is String -> return prefs.getString(key, default)   as T
                is Int -> return prefs.getInt(key, default)         as T
                is Float -> return prefs.getFloat(key, default)     as T
                is Boolean -> return prefs.getBoolean(key, default) as T
                is Long -> return prefs.getLong(key, default)       as T
                else -> {

                    val value = prefs.getString(key, null)

                    if (fromString != null && value != null) {
//                        logger.w("read as Object")
                        return fromString?.invoke(value) ?: default
                    }
                    else
                        if (default is String?) {
//                        logger.w("read $key as String?")
                            return prefs.getString(key, default) as T
                        }else {
//                        logger.w("read $key as default")
                            return default
                        }

                }
            }
        } catch (e: Exception) {
            return default
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    {
        val key = propName ?: property.name
        val value = readData(key)
        return value
    }


    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    {
        val key = propName ?: property.name
        saveData(key, value)
    }

}