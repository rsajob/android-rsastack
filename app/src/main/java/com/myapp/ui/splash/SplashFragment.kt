package com.myapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.myapp.ui.common.BaseFragment
import com.myapp.databinding.FragmentSplashBinding
import com.myapp.toothpick.DI
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.utils.visible
import toothpick.Toothpick

class SplashFragment : BaseFragment() , SplashView, BackButtonListener
{
    private var errorSnackbar: Snackbar? = null

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter(): SplashPresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(
        SplashPresenter::class.java)

    // =========== View Binding ================
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // =========================================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener { presenter.pressNext() }
    }

    override fun showProgress() {
        binding.progress.visible(true)
        errorSnackbar?.dismiss()
    }

    override fun showError(msg: String) {
        binding.progress.visible(false)
        errorSnackbar?.dismiss()

        view?.let {view ->
            errorSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Retry") { presenter.onRetry() }
                show()
            }
        }
    }

    override fun onBackPressed() {
        presenter.onBack()
    }
}