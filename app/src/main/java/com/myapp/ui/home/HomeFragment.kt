package com.myapp.ui.home

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentHomeBinding
import com.myapp.toothpick.DI
import com.myapp.ui.common.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class HomeFragment : BaseFragment(R.layout.fragment_home), HomeView {

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(
        HomePresenter::class.java)

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogout.setOnClickListener { presenter.pressLogout() }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
