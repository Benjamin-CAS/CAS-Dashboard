package com.cas.casdashboard.bg_work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cas.casdashboard.https.HttpsRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * @author Benjamin
 * @date 2021.9.7
 */
@HiltWorker
class RefreshLocationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val httpsRepo: HttpsRepo
): CoroutineWorker(appContext,workerParams) {
    override suspend fun doWork(): Result {
        Log.e(TAG, "doWork: ")
        withContext(Dispatchers.IO){
            try {
                httpsRepo.getAllCompany()
            }catch (e:Exception){
                Log.e(TAG, "doWork: ${e.printStackTrace()}")
            }
        }
        return Result.success()
    }
    companion object{
        const val TAG = "RefreshLocationWorker"
    }
}