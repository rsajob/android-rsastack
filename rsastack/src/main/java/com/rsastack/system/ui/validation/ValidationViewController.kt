package com.rsastack.system.ui.validation

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Aliaksandr Kazlou on 2/20/20.
 */
class ValidationViewController(
    private val onFieldEndOfChanging : (fieldId : String) -> Unit,
    private val onFieldChange : (fieldId: String, text: String?) -> Unit,
    private val getErrorString : (fieldId: String, validationState: ValidationState) -> String?
) {

    data class FieldHolder(val label: TextInputLayout, val field: EditText)
    private val fieldsMap = mutableMapOf<String, FieldHolder> ()
    private val watchers = mutableMapOf<String, OnChangedTextWatcher>()

    class OnChangedTextWatcher (val onChanged : (s: String?) -> Unit) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { s?.let {onChanged(it.toString()) } }
    }

    fun addView(id: String, field: EditText) {
        val label = field.parent.parent as TextInputLayout
        fieldsMap[id] = FieldHolder(label, field)

        field.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) onFieldEndOfChanging.invoke(id) }

        OnChangedTextWatcher { text -> onFieldChange.invoke(id, text)}
            .let {
                watchers[id] = it
                field.addTextChangedListener(it)
            }
    }

    fun showFieldError(fieldStates:HashMap<String, ValidationState>) {

        for ((fieldId, validationState) in fieldStates) {

            val validationResult = validationState.validationResult

            fieldsMap[fieldId]?.let {
                val hasError = validationResult != ValidatorResult.OK
                val errorString = if (hasError) getErrorString(fieldId, validationState) else null

                it.label.isErrorEnabled = hasError
                it.label.error = errorString
            }

        }

    }

    fun requestFocus(id: String) {
        fieldsMap[id]?.field?.requestFocus()
    }

    fun onDestroyView() {
        fieldsMap.forEach {
            val control = it.value.field
            val fieldId = it.key
            control.removeTextChangedListener(watchers[fieldId])
            control.onFocusChangeListener = null
        }
    }
}