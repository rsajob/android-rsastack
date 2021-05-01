package com.myapp.utils

import android.os.Build
import android.telephony.PhoneNumberUtils
import java.text.NumberFormat
import java.util.*


fun Float.formatPrice(currencyCode: String, defaultCurrencyCode: String = "RUB"): String {

    val amount = this

    var currency: Currency? = null

    try {
        currency = Currency.getInstance(currencyCode)
    } catch (e: Exception) {
        try {
            currency = Currency.getInstance(defaultCurrencyCode)
        } catch (e: Exception) {}
    }

    if (currency == null)
        return "$amount $currencyCode"

    try {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 0
        format.currency = currency
        return format.format(amount)
    } catch (e: Exception) {
        return "$amount $currencyCode"
    }
}

@Suppress("DEPRECATION")
fun String.formatPhone():String
{
    var phone = this
    if (!phone.startsWith("8") && !phone.startsWith("+7"))
        phone = "+7$phone"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        phone = PhoneNumberUtils.formatNumber(phone, "RU")
    }else
        phone = PhoneNumberUtils.formatNumber(phone)

    return phone
}