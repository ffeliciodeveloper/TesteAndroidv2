package br.com.testeAndroidV2.testeandroidv2.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Validator {

    fun formatDoubleToString(number: Double, showSymbol: Boolean = false): String {
        val numberFormat = NumberFormat.getCurrencyInstance(
            Locale("pt", "BR")
        )
        val symbol = numberFormat.currency!!.symbol
        return if (!showSymbol) {
            numberFormat.format(number).replace(symbol, "")
        } else {
            numberFormat.format(number)
        }
    }

    fun formatAgency(agency: String): String {
        var value = agency
        if (value.length > 7) {
            value = StringBuilder(value).insert(value.length - 1, "-")
                .insert(value.length - 7, ".")
                .toString()
        }
        return value
    }

    fun isOnlyNumber(value: String) : Boolean =
        value.replace(".", "")
             .replace("-", "")
             .replace("_", "")
             .matches("-?\\d+(\\.\\d+)?".toRegex())

    fun validatePassword(password: String): Boolean {
        val passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_@#%!*:;?$.,%]).{4,20})"
        return Pattern.compile(passwordPattern).matcher(password).matches()
    }

    fun validateCpf(value: String): Boolean {
        val cpfPattern = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")
        return cpfPattern.matcher(value).matches()
    }

    fun clearErrorText(TextInput: TextInputEditText, textInputLayout: TextInputLayout) {
        TextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayout.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    fun formatDate(dateString: String): String {
        val locale = Locale("pt", "BR")
        val dataEntry = SimpleDateFormat("yyyy-MM-dd", locale)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", locale)
        val date: Date? = dataEntry.parse(dateString)
        return dateFormat.format(date)
    }

}