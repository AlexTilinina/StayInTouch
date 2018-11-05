package ru.kpfu.itis.stayintouch.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class SoftKeyboardHelper {

    companion object {

        fun hideKeyboard(context: Context, view: View) {
            val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}