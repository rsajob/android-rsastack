package com.myapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.myapp.databinding.FragmentSplashBinding
import com.myapp.toothpick.DI
import com.myapp.ui.mvvmtest.provideViewModel
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.utils.visible
import kotlinx.coroutines.flow.collect

class SplashFragment : Fragment(), BackButtonListener
{
    private var errorSnackbar: Snackbar? = null

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        viewModel = provideViewModel(DI.TOP_FLOW_SCOPE, SplashViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { renderState(it) }
        }


        return binding.root
    }

    private fun renderState(state: State) = when (state) {
        is State.Progress -> showProgress()
        is State.Error -> showError(state.message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private lateinit var viewModel:SplashViewModel

    private fun showProgress() {
        binding.progress.visible(true)
        errorSnackbar?.dismiss()
    }

    private fun showError(msg: String) {
        binding.progress.visible(false)
        errorSnackbar?.dismiss()

        view?.let {view ->
            errorSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Retry") { viewModel.onRetry() }
                show()
            }
        }
    }

    override fun onBackPressed() {
        viewModel.onBack()
    }

}