package com.myapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.myapp.databinding.FragmentHomeBinding
import com.myapp.toothpick.DI
import com.myapp.ui.common.BaseFragment

import toothpick.Toothpick

class HomeFragment : BaseFragment(), HomeView {

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(
        HomePresenter::class.java)

    // =========== View Binding ================
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // =========================================


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogout.setOnClickListener { presenter.pressLogout() }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
