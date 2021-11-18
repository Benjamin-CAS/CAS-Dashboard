package com.cas.casdashboard.https

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.17 15:56
 */
class ApiResultCallAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        check(getRawType(returnType) == Call::class.java){ "返回值必须是retrofit2 Call类型" }
        check(returnType is ParameterizedType){ "返回至类型必须是ParameterizedType对应类型" }
        val apiResultType = getParameterUpperBound(0,returnType)
        check(getRawType(apiResultType) == ApiResult::class.java){ "返回包装类型必须是 ApiResult 类型" }
        check(apiResultType is ParameterizedType){ "返回包装类型必须是ParameterizedType对应类型" }
        val dataType = getParameterUpperBound(0,apiResultType)
        return ApiResultCallAdapter<Any>(dataType)
    }
}

/**
 * CallAdapter适配器，也就是将 T 转换为 ApiResult<T> 的适配器
 */
class ApiResultCallAdapter<T>(
    private val type: Type
): CallAdapter<T, Call<ApiResult<T>>> {

    override fun responseType(): Type = type

    override fun adapt(call: Call<T>): Call<ApiResult<T>> {
        return ApiResultCall(call)
    }

}

class ApiResultCall<T>(private val call:Call<T>):Call<ApiResult<T>>{
    override fun clone(): Call<ApiResult<T>> = ApiResultCall(call.clone())

    override fun execute(): Response<ApiResult<T>> {
        throw UnsupportedOperationException("不支持同步请求")
    }

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        call.enqueue(object :Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val result = if (response.body() == null) {
                        ApiResult.Failure(ApiError.dataIsNull)
                    } else {
                        ApiResult.Success(response.body()!!)
                    }
                    callback.onResponse(this@ApiResultCall, Response.success(result))
                } else {
                    val result = ApiResult.Failure(ApiError.httpStatusError)
                    Log.e(TAG, "网络请求失败")
                    callback.onResponse(this@ApiResultCall, Response.success(result))
                }

            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val failureResult = when (t) {
                    is ApiException -> {
                        ApiResult.Failure(t.error)
                    }
                    is SocketTimeoutException -> {
                        ApiResult.Failure(ApiError.timeoutError)
                    }
                    is ConnectException,is UnknownHostException -> {
                        ApiResult.Failure(ApiError.connectionError)
                    }
                    else -> {
                        ApiResult.Failure(ApiError.unknownError)
                    }
                }
                callback.onResponse(this@ApiResultCall, Response.success(failureResult))
            }

        })
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun cancel() {
        return call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }
    companion object {
        const val TAG = "ApiResultCall"
    }
}