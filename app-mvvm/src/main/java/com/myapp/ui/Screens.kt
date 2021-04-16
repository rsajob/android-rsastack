package com.myapp.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.AppScreen
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.myapp.R
import com.myapp.ui.splash.SplashFragment
import com.myapp.ui.maintabs.MainTabsFragment
import com.myapp.ui.auth.AuthFlowFragment
import com.myapp.ui.auth.phone.PhoneFragment
import com.myapp.ui.auth.sms.SmsFragment
import com.myapp.ui.cards.CardsFragment
import com.myapp.ui.home.HomeFragment
import com.myapp.ui.mvvmtest.LondonFragment

open class AppTabScreen(@IdRes val navigationIdRes: Int,
                        key: String? = null,
                        fragmentCreator: Creator<FragmentFactory, Fragment>
) : FragmentScreen (key, fragmentCreator)

object Screens {
    fun TopFlow() = FragmentScreen { TopFlowFragment() }
    fun Splash() = FragmentScreen { SplashFragment() }
    fun MainTabs() = FragmentScreen { MainTabsFragment() }
    fun AuthFlow() = FragmentScreen { AuthFlowFragment() }
    fun AuthPhone() = FragmentScreen { PhoneFragment() }
    fun AuthSms() = FragmentScreen { SmsFragment() }
    fun London() = FragmentScreen { LondonFragment() }
    fun TabHome() = AppTabScreen(R.id.navigation_home, "home") { HomeFragment() }
    fun TabContacts() = AppTabScreen(R.id.navigation_cards, "contacts") { CardsFragment() }

}