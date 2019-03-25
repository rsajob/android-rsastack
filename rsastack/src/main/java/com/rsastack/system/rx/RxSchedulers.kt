package com.rsastack.system.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulersProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
}

class RxSchedulers : SchedulersProvider {
    override fun io(): Scheduler =  Schedulers.io()
    override fun computation(): Scheduler = Schedulers.computation()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}