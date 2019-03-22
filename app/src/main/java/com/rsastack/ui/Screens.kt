package com.rsastack.ui

import androidx.annotation.IdRes
import com.rsastack.R
import com.rsastack.ui.splash.SplashFragment
import com.rsastack.ui.maintabs.MainTabsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import com.rsastack.ui.auth.AuthFlowFragment
import com.rsastack.ui.auth.phone.PhoneFragment
import com.rsastack.ui.auth.sms.SmsFragment
import com.rsastack.ui.cards.CardsFragment
import com.rsastack.ui.home.HomeFragment

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