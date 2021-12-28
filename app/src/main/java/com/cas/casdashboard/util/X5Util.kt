package com.cas.casdashboard.util

import android.annotation.SuppressLint
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import java.util.HashMap

object X5Util {
    @Suppress("NAME_SHADOWING", "DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    fun loadX5(webView: WebView){
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        val webSettings = webView.settings
        if (webSettings != null) {
            webSettings.apply {
                setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
                builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
                displayZoomControls = true //隐藏原生的缩放控件
                blockNetworkImage = false //解决图片不显示
                loadsImagesAutomatically = true //支持自动加载图片
                defaultTextEncodingName = "utf-8" //设置编码格式
                cacheMode = WebSettings.LOAD_NO_CACHE
                javaScriptCanOpenWindowsAutomatically = true  //支持通过JS打开新窗口
                useWideViewPort = true
                loadWithOverviewMode = true
                loadsImagesAutomatically = true; //支持自动加载图片
                domStorageEnabled = true// 设置支持DOM storage API
                javaScriptEnabled = true  // 让WebView能够执行javaScript
            }
            /**
             * html页面加载显示适应手机的屏幕大小
             */
            webView.apply{
                setBackgroundColor(0)
                clearCache(true)
                isDrawingCacheEnabled = true
            }
        }

        //该界面打开更多链接
    }
    /**
     * 腾讯 X5 刷新页面
     */
    fun reloadPage(webView: WebView){
        webView.reload()
    }
}