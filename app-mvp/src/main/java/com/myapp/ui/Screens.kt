package com.myapp.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.myapp.ui.auth.AuthFlowFragment
import com.myapp.ui.auth.phone.PhoneFragment
import com.myapp.ui.auth.sms.SmsFragment
import com.myapp.ui.maintabs.MainTabsFragment
import com.myapp.ui.splash.SplashFragment

object Screens {
    fun TopFlow() = FragmentScreen { TopFlowFragment() }
    fun Splash() = FragmentScreen { SplashFragment() }
    fun MainTabs() = FragmentScreen { MainTabsFragment() }
    fun AuthFlow() = FragmentScreen { AuthFlowFragment() }
    fun AuthPhone() = FragmentScreen { PhoneFragment() }
    fun AuthSms() = FragmentScreen { SmsFragment() }
}