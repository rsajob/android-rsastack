package com.myapp.ui.mvvmtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myapp.databinding.FragmentLondonBinding

class LondonFragment : Fragment() {

    private lateinit var viewModel:LondonViewModel

    // =========== View Binding ================
    private var _binding: FragmentLondonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentLondonBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(LondonViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // =========================================



}