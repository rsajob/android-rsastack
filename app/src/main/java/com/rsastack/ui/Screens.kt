package com.rsastack.ui

import com.rsastack.ui.splash.SplashFragment
import com.rsastack.ui.maintabs.MainTabsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import com.rsastack.ui.auth.AuthFlowFragment
import com.rsastack.ui.auth.phone.PhoneFragment
import com.rsastack.ui.auth.sms.SmsFragment
import com.rsastack.ui.cards.CardsFragment
import com.rsastack.ui.home.HomeFragment

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

    object TabCall : SupportAppScreen() {
        override fun getFragment() = HomeFragment()
    }

    object TabContacts : SupportAppScreen() {
        override fun getFragment() = CardsFragment()
    }

}