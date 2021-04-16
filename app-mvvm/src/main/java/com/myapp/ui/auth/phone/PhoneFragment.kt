package com.myapp.ui.auth.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import com.myapp.ui.common.BaseFragment
import com.myapp.databinding.FragmentAuthPhoneBinding
import com.myapp.toothpick.DI
import com.rsastack.system.utils.hideKeyboard
import com.rsastack.system.utils.visible

import toothpick.Toothpick

class PhoneFragment : BaseFragment(), PhoneView {

    @InjectPresenter
    lateinit var presenter: PhonePresenter

    @ProvidePresenter
    fun providePresenter(): PhonePresenter = Toothpick.openScope(DI.AUTH_FLOW_SCOPE).getInstance(
        PhonePresenter::class.java)

    // =========== View Binding ================
    private var _binding: FragmentAuthPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentAuthPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // =========================================

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
