package com.myapp.ui.maintabs

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.myapp.R
import com.myapp.ui.cards.CardsFragment
import com.myapp.ui.home.HomeFragment

interface AppTabScreen : FragmentScreen {
    val navigationIdRes: Int
    companion object {
        operator fun invoke(
            @IdRes navigationIdRes: Int,
            key: String? = null,
            fragmentCreator: Creator<FragmentFactory, Fragment>
        ) =
            object : AppTabScreen {
                override val screenKey = key ?: fragmentCreator::class.java.name
                override val navigationIdRes = navigationIdRes
                override fun createFragment(factory: FragmentFactory): Fragment = fragmentCreator.create(factory)
            }
    }
}

object Tabs
{
    const val HOME = "home"
    const val CARDS = "cards"

    fun Home() = AppTabScreen(R.id.navigation_home, HOME) { HomeFragment() }
    fun Cards() = AppTabScreen(R.id.navigation_cards, CARDS) { CardsFragment() }
}
