package com.cas.casdashboard.util

import android.util.Log

object LogUtil {
    private val isOutput = true
    @JvmStatic
    fun v(tag:String,msg: String) {
        log(LogLevel.V, tag,msg)
    }

    @JvmStatic
    fun d(tag:String,msg: String) {
        log(LogLevel.D, tag,msg)
    }

    @JvmStatic
    fun i(tag:String,msg: String) {
        log(LogLevel.I, tag,msg)
    }

    @JvmStatic
    fun w(tag:String,msg: String) {
        log(LogLevel.W, tag,msg)
    }

    @JvmStatic
    fun e(tag:String,msg: String) {
        log(LogLevel.E, tag,msg)
    }
    @JvmStatic
    fun error(tag:String,msg: String) {
        log(LogLevel.E, tag,msg)
    }

    private fun log(level: LogLevel,tag:String, msg: String) {
        if (isOutput)
            when(level){
                LogLevel.V -> Log.v("<---{$tag}--->", "<---[<--$msg-->]--->")
                LogLevel.D -> Log.d("<---{$tag}--->", "<---[<--$msg-->]--->")
                LogLevel.I -> Log.i("<---{$tag}--->", "<---[<--$msg-->]--->")
                LogLevel.W -> Log.w("<---{$tag}--->", "<---[<--$msg-->]--->")
                LogLevel.E -> Log.e("<---{$tag}--->", "<---[<--$msg-->]--->")
                else -> Log.e("<---{$tag}--->", "<---[<--$msg-->]--->")
            }
    }
    enum class LogLevel(val index: Int, val tag: String) {
        V(0, "V"),
        D(1, "D"),
        I(2, "I"),
        W(3, "W"),
        E(4, "E"),
        ERROR(5, "E"),
    }
}