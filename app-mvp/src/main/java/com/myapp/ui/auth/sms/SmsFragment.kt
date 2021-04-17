package com.myapp.ui.auth.sms

import android.os.Bundle
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentAuthSmsBinding
import com.myapp.toothpick.DI
import com.myapp.ui.common.BaseFragment
import com.rsastack.system.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class SmsFragment : BaseFragment(R.layout.fragment_auth_sms), SmsView {

    @InjectPresenter
    lateinit var presenter: SmsPresenter

    @ProvidePresenter
    fun providePresenter(): SmsPresenter = Toothpick.openScope(DI.AUTH_FLOW_SCOPE).getInstance(
        SmsPresenter::class.java)

    private val binding by viewBinding(FragmentAuthSmsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
    }

    private fun initControls() {
        binding.btnNext.setOnClickListener {
            val smsString = binding.phoneOrSms.text.toString()
            presenter.pressNext(smsString)
        }
        binding.btnCancel.setOnClickListener {
            presenter.pressCancel()
        }
    }

    override fun showProgress(shown:Boolean){
        binding.loginProgress.visible(shown)
    }

    override fun showMessage(msg: String) {
        binding.loginProgress.visible(false)
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
