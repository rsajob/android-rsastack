package com.rsastack.system.base

interface RealDestroyOwner {
    var realDestroyListener: RealDestroyListener?
}

interface RealDestroyListener {
    fun onRealDestroy() {}
}
