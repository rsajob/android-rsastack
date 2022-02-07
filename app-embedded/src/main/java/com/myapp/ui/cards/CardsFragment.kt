package com.myapp.ui.cards

import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentCardsBinding
import com.myapp.toothpick.DI
import com.myapp.ui.common.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class CardsFragment : BaseFragment(R.layout.fragment_cards), CardsView {

    @InjectPresenter
    lateinit var presenter: CardsPresenter

    @ProvidePresenter
    fun providePresenter(): CardsPresenter =
        Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(CardsPresenter::class.java)

    private val binding by viewBinding(FragmentCardsBinding::bind)

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
