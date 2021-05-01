package com.myapp.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Once
{
    private val mutex: Mutex = Mutex()
    private var error:Throwable? = null
    private var result:Any? = null

    // Выполняет блок кода один раз и синхронно (второй и последующие потоки получат тот же результат)
    @Suppress("UNCHECKED_CAST")
    suspend fun <T> run(block: suspend ()->T):T
    {
        error = null
        result = null

        return mutex.withLock {
            error?.let { throw it }
            result?.let { return@withLock result as T }
            try {
                result = block()
                result as T
            } catch (e: Throwable) {
                error = e
                throw e
            }
        }
    }

    suspend operator fun <T> invoke(block: suspend()->T):T = run(block)
}