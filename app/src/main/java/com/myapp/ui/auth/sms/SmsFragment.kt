package com.myapp.ui.auth.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.myapp.ui.common.BaseFragment
import com.myapp.databinding.FragmentAuthSmsBinding
import com.myapp.toothpick.DI
import com.rsastack.system.utils.visible

import toothpick.Toothpick

class SmsFragment : BaseFragment(), SmsView {

    @InjectPresenter
    lateinit var presenter: SmsPresenter

    @ProvidePresenter
    fun providePresenter(): SmsPresenter = Toothpick.openScope(DI.AUTH_FLOW_SCOPE).getInstance(
        SmsPresenter::class.java)

    // =========== View Binding ================
    private var _binding: FragmentAuthSmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentAuthSmsBinding.inflate(inflater, container, false)
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
