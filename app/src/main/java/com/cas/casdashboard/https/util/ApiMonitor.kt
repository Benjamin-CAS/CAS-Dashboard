package com.cas.casdashboard.https.util

import android.util.Log
import com.cas.casdashboard.https.util.RequestStatus
import com.cas.casdashboard.https.util.StateLiveData
import com.cas.casdashboard.util.CasEncDecPayload.decodeApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.30 13:46
 */

inline fun <T : BaseJson, reified R> httpRequest(stateLiveData: StateLiveData<R>, block: () -> T?) {
    try {
        stateLiveData.postValue(RequestStatus(status = RequestStatus.HttpState.LOADING))
        val data = block()
        val payload = decodePayload<R>(data?.payload?:"")
        val response = if (data != null) {
            RequestStatus(
                code = data.code,
                status = when (data.code) {
                    200 -> RequestStatus.HttpState.SUCCESS
                    in 500..1500 -> RequestStatus.HttpState.FAILED
                    else -> RequestStatus.HttpState.UNKNOWN
                },
                msg = data.msg,
                json = payload
            )
        } else {
            RequestStatus(status = RequestStatus.HttpState.EMPTY)
        }
        Log.e("httpRequest", "httpRequest:$response")
        stateLiveData.postValue(response)
    } catch (e: Exception) {
        e.printStackTrace()
        stateLiveData.postValue(RequestStatus(status = RequestStatus.HttpState.ERROR, error = e))
    }
}
inline fun <reified R> decodePayload(payload:String): R? {
    val type = object : TypeToken<R>(){}.type
    return Gson().fromJson(decodeApiResponse(payload),type)
}
abstract class BaseJson {
    abstract val status: Boolean
    abstract val code: Int
    abstract val payload: String
    abstract val msg: String
}