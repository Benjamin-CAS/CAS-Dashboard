package com.cas.casdashboard.frg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cas.casdashboard.https.repo.AppRepo
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
    fun getAdministrator(query: String) = viewModelScope.launch {
        httpRepo.getAdministrator(query)
    }
    fun getMonitorLocInfo() = httpRepo.getMonitorLocInfo()
}