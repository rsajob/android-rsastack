package com.rsastack.ui.auth.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_auth_phone.*
import com.rsastack.ui.common.BaseFragment
import com.rsastack.R
import com.rsastack.toothpick.DI
import com.rsastack.utils.hideKeyboard
import com.rsastack.utils.visible

import toothpick.Toothpick

class PhoneFragment : BaseFragment(), PhoneView {

    override val layoutRes = R.layout.fragment_auth_phone

    @InjectPresenter
    lateinit var presenter: PhonePresenter

    @ProvidePresenter
    fun providePresenter(): PhonePresenter = Toothpick.openScope(DI.AUTH_FLOW_SCOPE).getInstance(
        PhonePresenter::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
    }

    private fun initControls() {
        btn_next.setOnClickListener {
            hideKeyboard()
            val phoneString = phone_or_sms.text.toString()
            presenter.pressNext(phoneString)
        }
        btn_cancel.setOnClickListener {
            hideKeyboard()
            presenter.pressCancel()
        }
    }

    override fun showProgress(shown:Boolean){
        login_progress.visible(shown)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}
