package com.myapp.ui.mvvmtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LondonViewModelFactory(private val score:Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LondonViewModel::class.java)) {
            return LondonViewModel(score) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}