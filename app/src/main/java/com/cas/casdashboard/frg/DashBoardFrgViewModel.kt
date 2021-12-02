package com.cas.casdashboard.frg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cas.casdashboard.https.repo.AppRepo
import com.cas.casdashboard.https.response.decode.GetMonitorLocInfo
import com.cas.casdashboard.https.response.decode.LocGetData
import com.cas.casdashboard.https.util.StateLiveData
import com.cas.casdashboard.model.room.entity.Administrator
import com.cas.casdashboard.model.room.entity.GetMonitorLocInfoItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.18 17:01
 */
@HiltViewModel
class DashBoardFrgViewModel @Inject constructor(private val httpRepo: AppRepo): ViewModel() {
    val locDataGetIpad = StateLiveData<LocGetData>()
    val getMonitorLocInfo = StateLiveData<GetMonitorLocInfo>()
    private fun getLocDataGetIpad(
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
    private fun getMonitorLocInfo(
        companyID: String,
        locationId: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            httpRepo.getExtLocInfo(companyID, locationId,getMonitorLocInfo)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getAdministrator(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val admin = httpRepo.getAdministrator(query)
        if (admin != null) {
            getLocDataGetIpad(admin.companyId,admin.locationId,admin.username,admin.password)
            getMonitorLocInfo(admin.companyId,admin.locationId)
        }
    }
    fun insertGetMonitorLocInfo(getMonitorLocInfo: GetMonitorLocInfo) = viewModelScope.launch(Dispatchers.IO) {
        val monitorLocInfo = getMonitorLocInfo[0]
        httpRepo.insertMonitorLocInfo(GetMonitorLocInfoItemEntity(
            co2 = monitorLocInfo.co2,
            humidity = monitorLocInfo.humidity,
            lat = monitorLocInfo.lat,
            locationId = monitorLocInfo.locationId,
            logo = monitorLocInfo.logo,
            lon = monitorLocInfo.lon,
            picture = monitorLocInfo.picture,
            pm = monitorLocInfo.pm,
            temperature = monitorLocInfo.temperature,
            tvoc = monitorLocInfo.tvoc
        ))
    }
}