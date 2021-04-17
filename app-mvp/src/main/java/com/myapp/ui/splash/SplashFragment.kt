package com.myapp.ui.splash

import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.myapp.R
import com.myapp.databinding.FragmentSplashBinding
import com.myapp.toothpick.DI
import com.myapp.ui.common.BaseFragment
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class SplashFragment : BaseFragment(R.layout.fragment_splash) , SplashView, BackButtonListener
{
    private var errorSnackbar: Snackbar? = null

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter(): SplashPresenter = Toothpick.openScope(DI.TOP_FLOW_SCOPE).getInstance(
        SplashPresenter::class.java)

    private val binding by viewBinding(FragmentSplashBinding::bind)

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