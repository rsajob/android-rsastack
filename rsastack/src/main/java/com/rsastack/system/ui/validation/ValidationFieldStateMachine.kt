package com.rsastack.system.ui.validation

data class ValidationState(val validator:Validator, val validationResult:ValidatorResult)

/**
 * Created by Roman Savelev (aka @rsa) on 12.03.2018
 */
class ValidationFieldStateMachine (private val fieldController: FieldController ) {

    interface FieldController {
        fun showValidation(validationState: ValidationState)
        fun getValidator() : Validator
    }

    private interface State {
        fun change(value: String) {}
        fun endOfChanging() {}
        fun setCustomError(validationState:ValidationState) {}
        fun reset() {}
    }

    var lastValidationResult: ValidatorResult = ValidatorResult.OK
    var current: String = ""

    private var currentState: State = StateNormal()

    fun isValid() = lastValidationResult == ValidatorResult.OK

    fun checkValid() = fieldController.getValidator().validate(current) == ValidatorResult.OK

    fun change(value: String) {
        currentState.change(value)
    }

    fun endOfChanging() {
        currentState.endOfChanging()
    }

    fun reset()
    {
        currentState.reset()
    }

    fun setCustomError(validationState:ValidationState) {
        currentState.setCustomError(validationState)
    }

    private inner class StateNormal : State {

        override fun change(value: String) {
            current = value
        }

        override fun endOfChanging() {
            val validator = fieldController.getValidator()
            val validationResult = validator.validate(current)
            if (validationResult != ValidatorResult.OK) {
                currentState = StateError()
                fieldController.showValidation(ValidationState(validator, validationResult))
            }
            lastValidationResult = validationResult
        }

        override fun reset() {

        }

        override fun setCustomError(validationState: ValidationState) {
            currentState = StateError()
            fieldController.showValidation(validationState)
        }
    }

    private inner class StateError : State {

        override fun change(value:String) {
            current = value
            val validator = fieldController.getValidator()
            val validationResult = validator.validate(current)
            if (validationResult == ValidatorResult.OK) {
                currentState = StateNormal()
                fieldController.showValidation(ValidationState(validator, validationResult))
            }
            else if (validationResult != lastValidationResult) {
                fieldController.showValidation(ValidationState(validator, validationResult))
            }

            lastValidationResult = validationResult
        }

        override fun endOfChanging() {
            val validator = fieldController.getValidator()
            val validationResult = validator.validate(current)
            if (validationResult == ValidatorResult.OK) {
                currentState = StateNormal()
                fieldController.showValidation(ValidationState(validator, validationResult))
            }
            else if (validationResult != lastValidationResult) {
                fieldController.showValidation(ValidationState(validator, validationResult))
            }

            lastValidationResult = validationResult
        }

        override fun reset() {
            currentState = StateNormal()
            val validator = fieldController.getValidator()
            fieldController.showValidation(ValidationState(validator, ValidatorResult.OK))
        }

        override fun setCustomError(validationState: ValidationState) {
            currentState = StateError()
            fieldController.showValidation(validationState)
        }

    }
}