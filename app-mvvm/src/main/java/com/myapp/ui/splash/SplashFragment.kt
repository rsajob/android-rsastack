package com.myapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.myapp.R
import com.myapp.databinding.FragmentSplashBinding
import com.myapp.toothpick.DI
import com.rsastack.system.viewmodel.provideViewModel
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.utils.visible
import kotlinx.coroutines.flow.collect

class SplashFragment : Fragment(R.layout.fragment_splash), BackButtonListener
{
    private var errorSnackbar: Snackbar? = null

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(DI.TOP_FLOW_SCOPE, SplashViewModel::class.java)
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { renderState(it) }
        }
    }

    private fun renderState(state: State) = when (state) {
        is State.Progress -> showProgress()
        is State.Error -> showError(state.message)
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