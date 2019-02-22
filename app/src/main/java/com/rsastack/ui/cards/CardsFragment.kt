package com.rsastack.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rsastack.R
import com.rsastack.toothpick.DI
import com.rsastack.ui.common.BaseFragment

import toothpick.Toothpick

class CardsFragment : BaseFragment(), CardsView {

    override val layoutRes = R.layout.fragment_cards

    @InjectPresenter
    lateinit var presenter: CardsPresenter

    @ProvidePresenter
    fun providePresenter(): CardsPresenter =
        Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(CardsPresenter::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
