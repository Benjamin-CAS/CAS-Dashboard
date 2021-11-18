package com.cas.casdashboard.https

import android.content.res.Resources
import androidx.annotation.StringRes
import com.cas.casdashboard.R


/**
 * @author Benjamin
 * @description:
 * @date :2021.9.17 11:51
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data:T):ApiResult<T>()
    data class Failure(val apiResultError: ApiResultError):ApiResult<Nothing>()
}

object ApiError{
    private const val dataIsNullCode = 10
    private const val unknownCode = 11
    private const val dataTypeCode = 12
    private const val httpStatusCode = 13
    private const val timeoutCode = 14
    private const val connectionErrorCode = 15
    val dataIsNull = ApiResultError(errorMsgId = R.string.data_is_Null,errorCode = dataIsNullCode)
    val unknownError = ApiResultError(errorMsgId = R.string.description_unknown_error,errorCode = unknownCode)
    val connectionError = ApiResultError(errorMsgId = R.string.connection_error,errorCode = connectionErrorCode)
    val timeoutError = ApiResultError(errorMsgId = R.string.time_out_error,errorCode = timeoutCode)
    val dataTypeError = ApiResultError(errorMsgId = R.string.time_out_error,errorCode = dataTypeCode)
    val httpStatusError = ApiResultError(errorMsgId = R.string.http_status_error,errorCode = httpStatusCode)
}

data class ApiResultError(val errorMsg:String ?= null,
                          val errorMsgId:Int,
                          val errorCode:Int ?= ApiError.unknownError.errorCode) {
    fun getErrorMsgForApi() = if (errorMsg.isNullOrEmpty()) Resources.getSystem().getString(errorMsgId) else errorMsg
}

