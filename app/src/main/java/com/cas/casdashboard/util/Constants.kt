package com.cas.casdashboard.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData


object Constants {
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }
}