package com.myapp.utils

import com.google.gson.Gson

fun Any?.toJsonString():String {
    return Gson().toJson(this)
}
