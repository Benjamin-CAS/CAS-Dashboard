package com.cas.casdashboard.frg

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.cas.casdashboard.https.HttpsRepo
import com.cas.casdashboard.model.room.entity.CompanyAllEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class LoginFrgViewModel @Inject constructor(private val httpRepo: HttpsRepo): ViewModel() {
    private var searchCompany = MutableLiveData<String>()
    fun getAllCompany() = httpRepo.observeAllCompany()
    fun getSearchCompany() = searchCompany.switchMap {
        if (it.isNullOrBlank()) emptyFlow<List<CompanyAllEntity>>().asLiveData()
        else httpRepo.getSearchCompanyAllName(it).asLiveData()
    }
    fun searchCompany(query: String){
        searchCompany.postValue(query)
    }
}