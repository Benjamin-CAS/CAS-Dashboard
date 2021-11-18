package com.cas.casdashboard.frg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cas.casdashboard.https.AppRepo
import com.cas.casdashboard.https.response.decode.GetMonitorLocInfo
import com.cas.casdashboard.https.response.decode.LocGetData
import com.cas.casdashboard.https.util.StateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.18 17:01
 */
@HiltViewModel
class MonitoringFrgViewModel @Inject constructor(private val httpRepo: AppRepo): ViewModel() {
    val locDataGetIpad = StateLiveData<LocGetData>()
    val getMonitorLocInfo = StateLiveData<GetMonitorLocInfo>()
    fun getLocDataGetIpad(
        companyID: String,
        locationId: String,
        user:String,
        password: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            httpRepo.locDataGetIpad(companyID, locationId, user, password,locDataGetIpad)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
    fun getMonitorLocInfo(
        companyID: String,
        locationId: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            httpRepo.getExtLocInfo(companyID, locationId,getMonitorLocInfo)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getAdministrator(query: String) = httpRepo.getAdministrator(query).asLiveData()

}