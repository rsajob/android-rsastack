package com.rsastack.system.ui.validation

import java.util.regex.Pattern

/**
 * Created by Roman Savelev (aka @rsa) on 12.03.2018
 */

enum class ValidatorResult {
    OK,           // Успешная валидация
    FAIL_REQUIRE, // Поле не заполнено
    FAIL_MIN,     // Меньше допустимой длины
    FAIL_MAX,     // Превышает допустимую длину
    FAIL_PATTERN, // Значение не соотвтествует шаблону

    FAIL_VALUE    // Значение не прошло валидацию (например на сервере нет такого пользователя)
}

open class Validator (
    val required: Boolean = false,
    val min: Int = 0,
    val max: Int = Int.MAX_VALUE,
    var pattern: Pattern? = null
) {
    private fun isNotEmptyValid(value: String) = !required || value.isNotEmpty()
    private fun isPatternValid(value: String) = pattern == null || pattern?.matcher(value)!!.matches()
    private fun isMinLengthValid(value: String) = min == 0 || value.length >= min
    private fun isMaxLengthValid(value: String) = max == Int.MAX_VALUE || value.length <= max
    open fun validate(value: String): ValidatorResult {
        if (!isNotEmptyValid(value)) return ValidatorResult.FAIL_REQUIRE
        if (!isPatternValid(value)) return ValidatorResult.FAIL_PATTERN
        if (!isMaxLengthValid(value)) return ValidatorResult.FAIL_MAX
        if (!isMinLengthValid(value)) return ValidatorResult.FAIL_MIN
        return ValidatorResult.OK
    }
}

