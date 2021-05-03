package com.myapp.ui.auth.phone

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.R
import com.myapp.databinding.FragmentAuthPhoneBinding
import com.myapp.toothpick.DI
import com.rsastack.system.navigation.BackButtonListener
import com.rsastack.system.utils.hideKeyboard
import com.rsastack.system.utils.visible
import com.rsastack.system.mvvm.provideViewModel
import kotlinx.coroutines.flow.collect

class PhoneFragment : Fragment(R.layout.fragment_auth_phone), BackButtonListener {

    lateinit var viewModel: PhoneVM
    private val binding by viewBinding(FragmentAuthPhoneBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(DI.AUTH_FLOW_SCOPE, PhoneVM::class.java)
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
            hideKeyboard()
            val phoneString = binding.phoneOrSms.text.toString()
            viewModel.pressNext(phoneString)
        }
    }

    private fun renderState(state: PhoneVM.State) = when (state) {
        is PhoneVM.State.Idle -> {
            binding.loginProgress.visible(false)
        }
        is PhoneVM.State.Progress -> {
            binding.loginProgress.visible(true)
        }
        is PhoneVM.State.Error -> {
            binding.loginProgress.visible(false)
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

}
