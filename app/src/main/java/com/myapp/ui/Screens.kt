package com.myapp.ui

import androidx.annotation.IdRes
import com.myapp.R
import com.myapp.ui.splash.SplashFragment
import com.myapp.ui.maintabs.MainTabsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import com.myapp.ui.auth.AuthFlowFragment
import com.myapp.ui.auth.phone.PhoneFragment
import com.myapp.ui.auth.sms.SmsFragment
import com.myapp.ui.cards.CardsFragment
import com.myapp.ui.home.HomeFragment

open class SupportAppTabScreen(@IdRes val navigationIdRes: Int) : SupportAppScreen()

object Screens {

    object TopFlow : SupportAppScreen() {
        override fun getFragment() = TopFlowFragment()
    }

    object Splash : SupportAppScreen() {
        override fun getFragment() = SplashFragment()
    }

    object MainTabs : SupportAppScreen() {
        override fun getFragment() = MainTabsFragment()
    }

    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = AuthFlowFragment()
    }

    object AuthPhone : SupportAppScreen() {
        override fun getFragment() = PhoneFragment()
    }

    object AuthSms : SupportAppScreen() {
        override fun getFragment() = SmsFragment()
    }

    object TabHome : SupportAppTabScreen(R.id.navigation_home) {
        override fun getFragment() = HomeFragment()
    }

    object TabContacts : SupportAppTabScreen(R.id.navigation_cards) {
        override fun getFragment() = CardsFragment()
    }
}