package com.cas.casdashboard.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import com.cas.casdashboard.https.Api.Companion.LOCATION_INFO_METHOD_FOR_KEY


object Constants {
    const val API_APP_ID = 1
    const val NONCE = "aa"
    var isLoginView = false
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
    private suspend fun toBase64Encoding(encryptedPayload: String) =
        Base64.encodeToString(encryptedPayload.encodeToByteArray(), Base64.NO_PADDING).trim()
    suspend fun getEncryptedEncodedPayloadForIndoorLocation(timeStamp: String): String {
        val key = "${LOCATION_INFO_METHOD_FOR_KEY}$timeStamp"
        val payload = "ltime$timeStamp"
        Log.e(TAG, "KEY payload: $key $payload")
        val casEncrypted = doCASEncryptOrDecrypt(payload = payload, key = key)
        Log.e(
            TAG,
            "getEncryptedEncodedPayloadForIndoorLocation(timeStamp: $timeStamp) key $key payload $payload encrypted $casEncrypted"
        )
        return toBase64Encoding(casEncrypted)
    }
    private fun doCASEncryptOrDecrypt(payload: String, key: String): String {
        val trueKey = calculateEncKey(payload, key)
        var encodedStr = ""
        for ((index, letter) in payload.withIndex()) {
            val keyForCurrentPos = trueKey[index]
            val code = (keyForCurrentPos.code).xor(letter.code)
            encodedStr += if (code < 32 || code > 126) {
                letter
            } else code.toChar()
        }
        return encodedStr
    }
    private fun calculateEncKey(payload: String, key: String): String {
        var trueKey = ""
        val payLoadLength = payload.length
        while (trueKey.length < payLoadLength) {
            trueKey += key
        }
        return trueKey.substring(0, payload.length)
    }
    private const val TAG = "Constants"
}