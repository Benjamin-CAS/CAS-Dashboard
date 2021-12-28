package com.cas.casdashboard

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.tencent.mmkv.MMKV
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsListener
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App:Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        initX5()
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory).build()
    private fun initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //cb:内核
        val cb: QbSdk.PreInitCallback = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核
                Log.e("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                Log.e(TAG, "onCoreInitFinished: 完成加载")
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.setTbsListener(object : TbsListener {
            override fun onDownloadFinish(p0: Int) {
                //tbs内核下载完成回调
                //但是只有i等于100才算完成，否则失败
                //此时大概率可能由于网络问题
                //如果失败可增加网络监听器
                Log.e(TAG, "X5下载进度：$p0")
            }

            override fun onInstallFinish(p0: Int) {
                Log.e(TAG, "安装进度：$p0")
            }

            override fun onDownloadProgress(p0: Int) {
                Log.e(TAG, "下载进度：$p0")
            }

        })
    }
    companion object{
        const val TAG = "Application"
    }
}