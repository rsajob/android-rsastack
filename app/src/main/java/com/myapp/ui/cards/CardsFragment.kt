package com.myapp.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.myapp.databinding.FragmentCardsBinding
import com.myapp.toothpick.DI
import com.myapp.ui.common.BaseFragment

import toothpick.Toothpick

class CardsFragment : BaseFragment(), CardsView {

    @InjectPresenter
    lateinit var presenter: CardsPresenter

    @ProvidePresenter
    fun providePresenter(): CardsPresenter =
        Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(CardsPresenter::class.java)

    // =========== View Binding ================
    private var _binding: FragmentCardsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // =========================================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
