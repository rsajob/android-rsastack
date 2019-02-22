package com.rsastack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rsastack.R
import com.rsastack.toothpick.DI
import com.rsastack.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

import toothpick.Toothpick

class HomeFragment : BaseFragment(), HomeView {

    override val layoutRes = R.layout.fragment_home

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(
        HomePresenter::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_logout.setOnClickListener { presenter.pressLogout() }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
