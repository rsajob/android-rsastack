package com.myapp.ui.auth.phone

import android.os.Bundle
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentAuthPhoneBinding
import com.myapp.toothpick.DI
import com.myapp.ui.common.BaseFragment
import com.rsastack.system.utils.hideKeyboard
import com.rsastack.system.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class PhoneFragment : BaseFragment(R.layout.fragment_auth_phone), PhoneView {

    @InjectPresenter
    lateinit var presenter: PhonePresenter

    @ProvidePresenter
    fun providePresenter(): PhonePresenter = Toothpick.openScope(DI.AUTH_FLOW_SCOPE).getInstance(
        PhonePresenter::class.java)

    private val binding by viewBinding(FragmentAuthPhoneBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
    }

    private fun initControls() {
        binding.btnNext.setOnClickListener {
            hideKeyboard()
            val phoneString = binding.phoneOrSms.text.toString()
            presenter.pressNext(phoneString)
        }
        binding.btnCancel.setOnClickListener {
            hideKeyboard()
            presenter.pressCancel()
        }
    }

    override fun showProgress(shown:Boolean){
        binding.loginProgress.visible(shown)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
