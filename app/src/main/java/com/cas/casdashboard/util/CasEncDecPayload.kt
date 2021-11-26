package com.cas.casdashboard.util

import android.util.Base64
import android.util.Log
import com.cas.casdashboard.https.Api
import com.cas.casdashboard.https.request.*
import com.google.gson.Gson

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.14 18:12
 */
object CasEncDecPayload {
    // 加密
    private fun toBase64Encoding(encryptedPayload: String) =
        Base64.encodeToString(encryptedPayload.encodeToByteArray(), Base64.NO_PADDING).trim()
    fun decodeApiResponse(payload: String): String {
        return fromBase64Encoding(payload)
    }
    // 解密
    private fun fromBase64Encoding(base64EncodeStr: String) =
        Base64.decode(base64EncodeStr.encodeToByteArray(), Base64.NO_PADDING)
            .decodeToString()
            .trim()
    // Get Company All name
    fun getEncryptedEncodedPayloadForCompanyAll(timeStamp: String): String {
        val key = "${Api.LOCATION_INFO_METHOD_FOR_KEY}$timeStamp"
        val payload = "ltime$timeStamp"
        Log.e(TAG, "KEY payload: $key $payload")
        val casEncrypted = doCASEncryptOrDecrypt(payload = payload, key = key)
        Log.e(
            TAG,
            "getEncryptedEncodedPayloadForIndoorLocation(timeStamp: $timeStamp) key $key payload $payload encrypted $casEncrypted"
        )
        return toBase64Encoding(casEncrypted)
    }
    // Get Location for CompanyId
    fun getLocationEncryptedEncodedPayload(timeStamp: String,companyId:String):String{
        val key = "${Api.LOC_GET_IPAD_FOR_KEY}$timeStamp"
        val payload = Gson().toJson(LocationList(companyId,""))
        Log.e(TAG, "getLocationEncryptedEncodedPayload: $payload")
        val casEncrypted = doCASEncryptOrDecrypt(payload,key)
        Log.e(TAG, "getLocationEncryptedEncodedPayload: $casEncrypted")
        return toBase64Encoding(casEncrypted)
    }
    fun getLoginEncryptedEncodedPayload(timeStamp: String,locationId:String,username:String,password:String): String {
        val key = "${Api.GET_LOGIN_INTERFACE_FOR_KET}$timeStamp"
        val payload = Gson().toJson(GetLogin(locationId,username,password))
        Log.e(TAG, "getLoginEncryptedEncodedPayload: $payload")
        val casEncrypted = doCASEncryptOrDecrypt(payload,key)
        Log.e(TAG, "getLocationEncryptedEncodedPayload: $casEncrypted")
        return toBase64Encoding(casEncrypted)
    }
    fun getLocDataGetIpadEncryptedEncodedPayload(
        timeStamp: String,
        companyID: String,
        locationId: String,
        user:String,
        password: String
    ):String{
        val key = "${Api.LOC_DATA_GET_IPAD_KEY}$timeStamp"
        val payload = Gson().toJson(LocDataGetIpad(c = companyID,l = locationId,user = user,password = password))
        Log.e(TAG, "getLocDataGetIpadEncryptedEncodedPayload: $payload")
        val casEncrypted = doCASEncryptOrDecrypt(payload,key)
        return toBase64Encoding(casEncrypted)
    }
    fun getExtLocInfoEncryptedEncodedPayload(
        timeStamp: String,
        companyID: String,
        locationId: String
    ): String {
        val key = "${Api.GET_EXT_LOC_INFO_KEY}$timeStamp"
        val payload = Gson().toJson(RequestGetExtLocInfo(companyID,locationId))
        Log.e(TAG, "getExtLocInfoEncryptedEncodedPayload: $payload")
        val casEncrypted = doCASEncryptOrDecrypt(payload, key)
        return toBase64Encoding(casEncrypted)
    }
    fun getInterfaceDetailsEncryptedEncodedPayload(
        timeStamp: String,
        dashBoardId:String,
        username:String,
        password:String
    ): String {
        val key = "${Api.GET_INTERFACE_DETAILS_FOR_KEY}$timeStamp"
        val payload = Gson().toJson(RequestGetInterfaceDetails(dashBoardId,username,password))
        val casEncrypted = doCASEncryptOrDecrypt(payload, key)
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
    private const val TAG = "CasEncDecPayload"
}