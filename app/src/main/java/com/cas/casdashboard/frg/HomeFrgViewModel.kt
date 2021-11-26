package com.cas.casdashboard.frg

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cas.casdashboard.https.repo.AppRepo
import com.cas.casdashboard.https.util.decodePayload
import com.cas.casdashboard.model.room.entity.Administrator
import com.tencent.mmkv.MMKV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.11 11:04
 */
@HiltViewModel
class HomeFrgViewModel  @Inject constructor(private val httpRepo: AppRepo): ViewModel() {
    private val mk: MMKV = MMKV.defaultMMKV()
    fun getLoginResultItem() = httpRepo.getLoginResultItem()
    fun getAdministrator(query:String,success:(Administrator) -> Unit) = viewModelScope.launch {
        success(httpRepo.getAdministrator(query))
    }
    fun getInterfaceDetails(
        dashBoardId:String,
        username:String,
        password:String
    ) = viewModelScope.launch {
        Log.e("getInterfaceDetails", "getInterfaceDetails: ${httpRepo.getInterfaceDetails(dashBoardId, username, password)}")
    }
}