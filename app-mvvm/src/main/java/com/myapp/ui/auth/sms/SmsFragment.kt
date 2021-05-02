package com.myapp.ui.auth.sms

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentAuthSmsBinding
import com.myapp.toothpick.DI
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.utils.visible
import com.rsastack.system.viewmodel.provideViewModel
import kotlinx.coroutines.flow.collect

class SmsFragment : Fragment(R.layout.fragment_auth_sms), BackButtonListener {

    lateinit var viewModel: SmsVM
    private val binding by viewBinding(FragmentAuthSmsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(DI.AUTH_FLOW_SCOPE, SmsVM::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { renderState(it) }
        }
    }

    private fun initControls() {
        binding.btnNext.setOnClickListener {
            val smsString = binding.phoneOrSms.text.toString()
            viewModel.pressNext(smsString)
        }
        binding.btnCancel.setOnClickListener {
            viewModel.pressCancel()
        }
    }

    private fun renderState(state: SmsVM.State) = when (state) {
        is SmsVM.State.Idle -> {
            binding.loginProgress.visible(false)
        }
        is SmsVM.State.Progress -> {
            binding.loginProgress.visible(true)
        }
        is SmsVM.State.Error -> {
            binding.loginProgress.visible(false)
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

}
