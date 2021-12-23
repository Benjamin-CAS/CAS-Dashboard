package com.cas.casdashboard.frg

import androidx.lifecycle.*
import com.cas.casdashboard.https.repo.AppRepo
import com.cas.casdashboard.https.response.decode.CompanyLocationDecode
import com.cas.casdashboard.https.response.decode.LoginResultItem
import com.cas.casdashboard.https.util.StateLiveData
import com.cas.casdashboard.model.room.entity.Administrator
import com.cas.casdashboard.model.room.entity.CompanyAllEntity
import com.tencent.mmkv.MMKV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginFrgViewModel @Inject constructor(private val httpRepo: AppRepo): ViewModel() {
    private var searchCompany = MutableLiveData<String>()
    private val _loadingObserver = MutableLiveData<Boolean>()
    val loadingObserver:LiveData<Boolean> get() = _loadingObserver
    private val mk: MMKV = MMKV.defaultMMKV()
    private val _isRememberCredentials = MutableLiveData(false)
    private val _isLockedMode = MutableLiveData(false)
    val isRememberCredentials:LiveData<Boolean> get() = _isRememberCredentials
    val isLockedMode:LiveData<Boolean> get() = _isLockedMode
    val loginResult = StateLiveData<List<LoginResultItem>>()
    val getCompanyLocationID = StateLiveData<CompanyLocationDecode>()
    fun getAllCompany() = httpRepo.observeAllCompany()
    fun postIsRememberCredentialsValue() = _isRememberCredentials.postValue(mk.decodeBool(IS_REMEMBER_CREDENTIALS))
    fun getIsRememberCredentialsValue() = mk.decodeBool(IS_REMEMBER_CREDENTIALS)
    fun encodeIsRememberCredentialsValue(value:Boolean) = mk.encode(IS_REMEMBER_CREDENTIALS,value)
    fun postIsLockedModeValue() = _isLockedMode.postValue(mk.decodeBool(IS_LOCKED_MODE))
    fun encodeIsLockedModeValue(value:Boolean) = mk.encode(IS_LOCKED_MODE,value)
    fun searchCompany(query: String) = searchCompany.postValue(query)
    fun getSearchCompany() = searchCompany.switchMap {
        if (it.isNullOrBlank()) emptyFlow<List<CompanyAllEntity>>().asLiveData()
        else httpRepo.getSearchCompanyAllName(it).asLiveData()
    }
    fun getCompanyLocation(companyId:String) = viewModelScope.launch(Dispatchers.IO) {
        httpRepo.getCompanyLocation(companyId,getCompanyLocationID)
    }
    fun insertAdministrator(companyNameSearch:String,companyId:String,spinner:String,locationId:String,username: String,password: String) =
         viewModelScope.launch(Dispatchers.IO) {
             httpRepo.insertAdministrator(Administrator(companyNameSearch,companyId,spinner,locationId,username,password))
         }
    fun getAdministrator(query: String,success:(Administrator) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val admin:Administrator ?= httpRepo.getAdministrator(query)
        withContext(Dispatchers.Main){
            if (admin != null) {
                success(admin)
            }
        }
    }

    fun insertLoginResultItem(loginResultItem: List<LoginResultItem>) = viewModelScope.launch(Dispatchers.IO) {
        httpRepo.insertLoginResultItem(loginResultItem)
    }
    fun deleteAllLoginResultItem() = viewModelScope.launch {
        httpRepo.deleteAllLoginResultItem()
    }
    fun getLogin(locationId:String,username:String,password:String) = viewModelScope.launch(Dispatchers.IO) {
        httpRepo.getLogin(locationId,username,password,loginResult)
    }
    fun setLoadingObserver(value:Boolean) = _loadingObserver.postValue(value)
    companion object {
        const val TAG = "LoginFrgViewModel"
        private const val IS_REMEMBER_CREDENTIALS = "IS_REMEMBER_CREDENTIALS"
        private const val IS_LOCKED_MODE = "IS_LOCKED_MODE"
    }
}