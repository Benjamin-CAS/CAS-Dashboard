package com.cas.casdashboard.frg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cas.casdashboard.https.repo.ApiRepo
import com.cas.casdashboard.https.repo.AppRepo
import com.cas.casdashboard.https.response.decode.*
import com.cas.casdashboard.https.util.StateLiveData
import com.cas.casdashboard.model.room.entity.GetMonitorLocInfoItemEntity
import com.cas.casdashboard.util.CasEncDecPayload
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
class DashBoardFrgViewModel @Inject constructor(private val appRepo: AppRepo, private val apiRepo: ApiRepo): ViewModel() {
    val locDataGetIpad = StateLiveData<LocGetData>()
    val getMonitorLocInfo = StateLiveData<GetMonitorLocInfo>()
    val last72h = StateLiveData<HistoryLast72HData>()
    val lastWeek = StateLiveData<HistoryLastWeekData>()
    val lastMonth = StateLiveData<HistoryLastMonthData>()
    private fun getLocDataGetIpad(
        companyID: String,
        locationId: String,
        user:String,
        password: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            apiRepo.locDataGetIpad(companyID, locationId, user, password,locDataGetIpad)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
    private fun getLocDataGetIpadHistory(
        companyID: String,
        locationId: String,
        user:String,
        password: String,
        stateLastMonthLiveData: StateLiveData<HistoryLastMonthData>,
        stateLastWeekLiveData: StateLiveData<HistoryLastWeekData>,
        stateLast72HLiveData: StateLiveData<HistoryLast72HData>
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            apiRepo.locDataGetIpadLastMonthHistory(companyID, locationId, user, password,stateLastMonthLiveData)
            apiRepo.locDataGetIpadLastWeekHistory(companyID,locationId, user, password,stateLastWeekLiveData)
            apiRepo.locDataGetIpadLast72HHistory(companyID,locationId, user, password,stateLast72HLiveData)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
    private fun getMonitorLocInfo(
        companyID: String,
        locationId: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            apiRepo.getExtLocInfo(companyID, locationId,getMonitorLocInfo)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getAdministrator(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val admin = appRepo.getAdministrator(query)
        if (admin != null) {
            getLocDataGetIpad(admin.companyId,admin.locationId,admin.username,admin.password)
            getLocDataGetIpadHistory(admin.companyId,admin.locationId,admin.username,admin.password,lastMonth,lastWeek,last72h)
            getMonitorLocInfo(admin.companyId,admin.locationId)
        }
    }
    fun insertGetMonitorLocInfo(getMonitorLocInfo: GetMonitorLocInfo) = viewModelScope.launch(Dispatchers.IO) {
        val monitorLocInfo = getMonitorLocInfo[0]
        appRepo.insertMonitorLocInfo(
            GetMonitorLocInfoItemEntity(
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
            )
        )
    }
}