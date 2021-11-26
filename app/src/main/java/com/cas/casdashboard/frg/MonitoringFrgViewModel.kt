package com.cas.casdashboard.frg

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cas.casdashboard.https.repo.AppRepo
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
    fun getAdministrator(query:String,pageId:String) = viewModelScope.launch {
        val admin = httpRepo.getAdministrator(query)
        getInterfaceDetails(pageId,admin.username,admin.password)
    }
    private fun getInterfaceDetails(
        dashBoardId:String,
        username:String,
        password:String
    ) = viewModelScope.launch {
        Log.e("getInterfaceDetails", "getInterfaceDetails: ${httpRepo.getInterfaceDetails(dashBoardId, username, password)}")
    }
    fun getMonitorLocInfo() = httpRepo.getMonitorLocInfo()
}