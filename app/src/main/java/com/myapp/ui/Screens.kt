package com.myapp.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
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

open class AppTabScreen(@IdRes val navigationIdRes: Int,
                        key: String? = null,
                        fragmentCreator: Creator<FragmentFactory, Fragment>
) : FragmentScreen (key, fragmentCreator)

open class BaseFragmentScreen(fragmentCreator: Creator<FragmentFactory, Fragment>) : FragmentScreen(null, fragmentCreator)

object Screens {
    class TopFlow : BaseFragmentScreen( { TopFlowFragment() })
    class Splash : BaseFragmentScreen( { SplashFragment() } )
    class MainTabs : BaseFragmentScreen( { MainTabsFragment() } )
    class AuthFlow : BaseFragmentScreen( { AuthFlowFragment() } )
    class AuthPhone : BaseFragmentScreen( { PhoneFragment() } )
    class AuthSms : BaseFragmentScreen( { SmsFragment() } )
    class TabHome : AppTabScreen(R.id.navigation_home, "home", { HomeFragment() } )
    class TabContacts : AppTabScreen(R.id.navigation_cards, "contacts", { CardsFragment() } )
}