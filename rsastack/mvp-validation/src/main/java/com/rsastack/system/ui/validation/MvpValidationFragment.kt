package com.rsastack.system.ui.validation

import android.os.Bundle
import android.view.View
import com.rsastack.system.mvp.MvpFragment
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

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

abstract class MvpValidationFragment : MvpFragment(), ValidationView {

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