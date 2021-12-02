package com.cas.casdashboard.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import java.text.DecimalFormat


object Constants {
    const val API_APP_ID = 1
    const val NONCE = "aa"
    var isLoginView = false
    const val L_TIME_KEY = "ltime"
    const val PAYLOAD_KEY = "pl"
    var pageId = "0"
    var companyName = ""
    var companyLogo = ""
    private const val IMG_LOGO_URL = "https://monitor.cleanairspaces.com/assets/images/logo/"
    private const val IMG_BACKGROUND_IMAGE_URL = "https://monitor.cleanairspaces.com/assets/images/picture/"
    val isLockedMode = MutableLiveData(true)
    val String.BACKGROUND
        get() = IMG_BACKGROUND_IMAGE_URL.plus(this)
    val String.LOGO
        get() = IMG_LOGO_URL.plus(this)
    /**
     * 隐藏软键盘
     */
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }
    // dp转px 达到适配
    val Int.px:Float
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    val Float.px
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this,
            Resources.getSystem().displayMetrics
        )
    val Int.textPx:Float
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    val Float.textPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, this,
            Resources.getSystem().displayMetrics
        )
    val Double.formats: String
        get() = DecimalFormat("0.#").format(this)
    val Float.formats: String
        get() = DecimalFormat("0.#").format(this)
    private const val TAG = "Constants"
}