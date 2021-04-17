package com.myapp.ui.mvvmtest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentLondonBinding
import com.myapp.toothpick.DI

class LondonFragment : Fragment(R.layout.fragment_london) {

    private lateinit var viewModel:LondonViewModel

    private val binding by viewBinding(FragmentLondonBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(DI.TOP_FLOW_SCOPE, LondonViewModel::class.java) {
            bind(ScoreHolder::class.java).toInstance(ScoreHolder(3))
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.counter.text = viewModel.score.toString()
        binding.buttonNext.setOnClickListener { viewModel.pressNext() }
        binding.buttonPrev.setOnClickListener { viewModel.pressBack() }
    }

}