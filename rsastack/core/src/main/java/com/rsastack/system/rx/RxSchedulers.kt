package com.rsastack.system.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulersProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun trampoline(): Scheduler
    fun newThread(): Scheduler
    fun main(): Scheduler
}

class RxSchedulers : SchedulersProvider {
    override fun io(): Scheduler =  Schedulers.io()
    override fun computation(): Scheduler = Schedulers.computation()
    override fun trampoline() = Schedulers.trampoline()
    override fun newThread() = Schedulers.newThread()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}

class TestSchedulers : SchedulersProvider {
    override fun io() = Schedulers.trampoline()
    override fun computation() = Schedulers.trampoline()
    override fun trampoline() = Schedulers.trampoline()
    override fun newThread() = Schedulers.trampoline()
    override fun main() = Schedulers.trampoline()
}