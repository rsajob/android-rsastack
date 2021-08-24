package com.myapp.ui.maintabs

import androidx.lifecycle.ViewModel
import com.rsastack.system.navigation.FlowRouter
import javax.inject.Inject

class MainTabsViewModel @Inject constructor(
    private val router: FlowRouter
) : ViewModel() {

    fun onBackPressed() {
        router.exit()
    }

}
