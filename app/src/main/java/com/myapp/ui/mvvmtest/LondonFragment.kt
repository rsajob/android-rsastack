package com.myapp.ui.mvvmtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myapp.databinding.FragmentLondonBinding
import com.myapp.toothpick.DI

class LondonFragment : Fragment() {

    private lateinit var viewModel:LondonViewModel

    // =========== View Binding ================
    private var _binding: FragmentLondonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentLondonBinding.inflate(inflater, container, false)

        viewModel = provideViewModel(DI.TOP_FLOW_SCOPE, LondonViewModel::class.java) {
            bind(ScoreHolder::class.java).toInstance(ScoreHolder(3))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.counter.text = viewModel.score.toString()
        binding.buttonNext.setOnClickListener { viewModel.pressNext() }
        binding.buttonPrev.setOnClickListener { viewModel.pressBack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // =========================================


}