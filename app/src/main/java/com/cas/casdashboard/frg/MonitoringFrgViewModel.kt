package com.cas.casdashboard.frg

import android.util.Log
import androidx.lifecycle.*
import com.cas.casdashboard.https.repo.AppRepo
import com.cas.casdashboard.https.response.decode.InterfaceDetails
import com.cas.casdashboard.https.util.StateLiveData
import com.cas.casdashboard.model.room.entity.Administrator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.26 10:47
 */
@HiltViewModel
class MonitoringFrgViewModel @Inject constructor(private val httpRepo: AppRepo):ViewModel() {
    private val _isHideProgress = MutableLiveData<Boolean>()
    val isHideProgress:LiveData<Boolean> get() = _isHideProgress
    val monitoringDeviceData = StateLiveData<InterfaceDetails>()
    private val _isHideSelectRv = MutableLiveData<Boolean>()
    val isHideSelectRv:LiveData<Boolean> get() = _isHideSelectRv
    fun getAdministrator(query:String,pageId:String) = viewModelScope.launch {
        val admin = httpRepo.getAdministrator(query)
        if (admin != null) {
            getInterfaceDetails(pageId,admin.username,admin.password)
        }
    }
    private fun getInterfaceDetails(
        dashBoardId:String,
        username:String,
        password:String
    ) = viewModelScope.launch {
        httpRepo.getInterfaceDetails(dashBoardId,username,password,monitoringDeviceData)
    }
    fun postValueToIsHideProgress(value:Boolean) = _isHideProgress.postValue(value)
    fun postValueToIsHideSelectRv(value:Boolean) = _isHideSelectRv.postValue(value)
    fun getMonitorLocInfo() = httpRepo.getMonitorLocInfo()
}