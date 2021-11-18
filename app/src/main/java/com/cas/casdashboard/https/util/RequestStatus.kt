package com.cas.casdashboard.https.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.28 11:08
 */
class RequestStatus<T> (
    var code:Int = 0,
    var error:Throwable ?= null,
    var msg:String ?= null,
    var status: HttpState = HttpState.CREATE,
    var json:T ?= null
){
    enum class HttpState {
        CREATE, // 创建
        LOADING, // 加载中(网络请求中)
        SUCCESS, // 成功(请求成功)
        EMPTY, // 数据为null
        FAILED, // 接口请求成功服务器返回error
        ERROR, // 请求失败
        UNKNOWN // 未知
    }
}
class StateLiveData<T>:MutableLiveData<RequestStatus<T>>()
abstract class IStateObserver<T>:Observer<RequestStatus<T>>{
    override fun onChanged(t: RequestStatus<T>) {
        when(t.status){
            RequestStatus.HttpState.LOADING -> {
                onLoading()
            }
            RequestStatus.HttpState.SUCCESS -> {
                // 请求成功 数据不为空
                t.json?.let {
                    onDataChange(it)
                }
            }
            RequestStatus.HttpState.EMPTY -> {
                //数据为空
                onDataEmpty()
            }
            RequestStatus.HttpState.FAILED -> {
                t.msg?.let {
                    onFailed(it)
                }
            }
            RequestStatus.HttpState.ERROR -> {
                //请求错误
                t.error?.let { onError(it) }
            }
            else -> {
                onFailed("Unknown error,The server is not responding")
            }
        }
    }

    abstract fun onLoading()

    abstract fun onDataChange(data: T)

    abstract fun onDataEmpty()

    abstract fun onFailed(msg: String)

    abstract fun onError(error: Throwable)
}