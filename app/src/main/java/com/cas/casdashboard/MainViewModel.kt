package com.cas.casdashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.cas.casdashboard.bg_work.RefreshLocationWorker
import com.cas.casdashboard.https.AppRepo
import com.cas.casdashboard.https.util.StateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MainViewModel @Inject constructor(private val httpRepo: AppRepo, private val worker: WorkManager): ViewModel() {
     fun scheduleDataRefresh() {
          Log.e(TAG, "scheduleDataRefresh: 执行了")
          val constraints = Constraints.Builder()
               .setRequiredNetworkType(NetworkType.CONNECTED)
               .build()
          val refreshDataRequest = PeriodicWorkRequestBuilder<RefreshLocationWorker>(15, TimeUnit.MINUTES)
               .setConstraints(constraints)
               .build()
          // 防止重复
          worker.enqueueUniquePeriodicWork(
               DATA_REFRESHER_WORKER_NAME,
               ExistingPeriodicWorkPolicy.REPLACE,
               refreshDataRequest
          )
          worker.getWorkInfoByIdLiveData(refreshDataRequest.id).observeForever {
//               Log.e(TAG, "scheduleDataRefresh: ${it.state.name}")

          }

     }

     companion object{
          const val TAG = "MainViewModel"
          const val DATA_REFRESHER_WORKER_NAME = "DATA_REFRESHER_WORKER_NAME"
     }
}