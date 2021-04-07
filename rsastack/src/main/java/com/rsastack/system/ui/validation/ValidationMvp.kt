package com.rsastack.system.ui.validation

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.rsastack.system.moxy.MvpAppCompatFragment

/**
 * Created by Aliaksandr Kazlou on 2/24/20.
 */

interface ValidationView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setupValidationState(validationState:HashMap<String, ValidationState>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun requestFocus(id: String)
}

interface ValidationPresenter {
    fun onChange(id: String, value: String?)
    fun onEndOfChanging(id:String)
}

abstract class ValidationAppCompatFragment : MvpAppCompatFragment(), ValidationView {

    protected lateinit var validationViewController : ValidationViewController
    abstract fun getValidationErrorString(fieldId: String, validationState: ValidationState): String

    override fun onDestroyView() {
        super.onDestroyView()
        validationViewController.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validationViewController = ValidationViewController(
            { getValidationPresenter().onEndOfChanging(it) },
            { fieldId: String, text: String? -> getValidationPresenter().onChange(fieldId, text) },
            { fieldId, validationState -> getValidationErrorString(fieldId, validationState) }
        )
    }

    abstract fun getValidationPresenter() : ValidationPresenter

    // Validation interface
    override fun setupValidationState(validationState:HashMap<String, ValidationState>) {
        validationViewController.showFieldError(validationState)
    }

    override fun requestFocus(id: String) {
        validationViewController.requestFocus(id)
    }
}