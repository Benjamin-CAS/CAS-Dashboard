package com.cas.casdashboard.https.repo

import android.util.Log
import androidx.lifecycle.asLiveData
import com.cas.casdashboard.https.Api
import com.cas.casdashboard.https.response.decode.*
import com.cas.casdashboard.https.util.StateLiveData
import com.cas.casdashboard.https.util.httpRequest
import com.cas.casdashboard.model.room.dao.AdministratorDao
import com.cas.casdashboard.model.room.dao.CompanyAllEntityDao
import com.cas.casdashboard.model.room.dao.GetMonitorLocInfoDao
import com.cas.casdashboard.model.room.dao.LoginResultItemDao
import com.cas.casdashboard.model.room.entity.Administrator
import com.cas.casdashboard.model.room.entity.CompanyAllEntity
import com.cas.casdashboard.model.room.entity.GetMonitorLocInfoItemEntity
import com.cas.casdashboard.util.CasEncDecPayload
import com.cas.casdashboard.util.CasEncDecPayload.decodeApiResponse
import com.cas.casdashboard.util.CasEncDecPayload.getEncryptedEncodedPayloadForCompanyAll
import com.cas.casdashboard.util.CasEncDecPayload.getExtLocInfoEncryptedEncodedPayload
import com.cas.casdashboard.util.CasEncDecPayload.getInterfaceDetailsEncryptedEncodedPayload
import com.cas.casdashboard.util.CasEncDecPayload.getLocDataGetIpadEncryptedEncodedPayload
import com.cas.casdashboard.util.CasEncDecPayload.getLocationEncryptedEncodedPayload
import com.cas.casdashboard.util.CasEncDecPayload.getLoginEncryptedEncodedPayload
import com.cas.casdashboard.util.Constants.L_TIME_KEY
import com.cas.casdashboard.util.Constants.PAYLOAD_KEY
import com.google.gson.JsonObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepo @Inject constructor(
    private val api: Api,
    private val companyAllEntityDao: CompanyAllEntityDao,
    private val administratorDao: AdministratorDao,
    private val loginResultItemDao:LoginResultItemDao,
    private val getMonitorLocInfoDao: GetMonitorLocInfoDao
) {
    // 获取所有公司name
    suspend fun getAllCompany() {
        val timeStamp = System.currentTimeMillis().toString()
        val allCompanyResult =
            api.getAllCompany(payload = getEncryptedEncodedPayloadForCompanyAll(timeStamp))
        companyAllEntityDao.deleteAllCompanyAllEntity()
        for (indoorLocation in allCompanyResult.data) {
            companyAllEntityDao.insertCompany(
                CompanyAllEntity(
                    companyAllId = indoorLocation.company_id,
                    companyAllName = indoorLocation.name_en,
                    active = indoorLocation.active,
                    outDoor = indoorLocation.outdoor,
                    secure = indoorLocation.secure
                )
            )
        }
    }

    fun observeAllCompany() = companyAllEntityDao.getAllCompany().asLiveData()
    fun getSearchCompanyAllName(query: String) = companyAllEntityDao.getSearchCompanyAllName(query)

    suspend fun insertAdministrator(administrator: Administrator) = administratorDao.insertAdministrator(administrator)

    suspend fun getAdministrator(query: String) = administratorDao.getAdministrator(query)

    suspend fun insertLoginResultItem(loginResultItem: List<LoginResultItem>) = loginResultItemDao.insertLoginResultItem(loginResultItem)
    fun getLoginResultItem() = loginResultItemDao.getAllLoginResultItem().asLiveData()
    suspend fun deleteAllLoginResultItem() = loginResultItemDao.deleteAllLoginResultItem()
    suspend fun insertMonitorLocInfo(getMonitorLocInfo: GetMonitorLocInfoItemEntity) = getMonitorLocInfoDao.insertMonitorLocInfo(getMonitorLocInfo)
    fun getMonitorLocInfo() = getMonitorLocInfoDao.getAllMonitorLocInfo().asLiveData()
    suspend fun getCompanyLocation(companyID: String, stateLiveData:StateLiveData<CompanyLocationDecode>){
        val timeStamp = System.currentTimeMillis().toString()
        val locationEncryptedEncodedPayload =
            getLocationEncryptedEncodedPayload(timeStamp, companyID)
        val requestGetCompanyLocationData = JsonObject().apply {
            addProperty(L_TIME_KEY, timeStamp)
            addProperty(PAYLOAD_KEY, locationEncryptedEncodedPayload)
        }
        httpRequest(stateLiveData){
            api.getCompanyLocation(pl = requestGetCompanyLocationData)
        }
    }

    suspend fun getLogin(
        locationId: String,
        username: String,
        password: String,
        stateLiveData: StateLiveData<List<LoginResultItem>>
    ) {
        val timeStamp = System.currentTimeMillis().toString()
        val loginEncryptedEncodedPayload =
            getLoginEncryptedEncodedPayload(timeStamp, locationId, username, password)
        val requestGetLoginData = JsonObject().apply {
            addProperty(L_TIME_KEY, timeStamp)
            addProperty(PAYLOAD_KEY, loginEncryptedEncodedPayload)
        }
        httpRequest(stateLiveData) {
            api.getLogin(pl = requestGetLoginData)
        }
    }
    suspend fun locDataGetIpad(
        companyID: String,
        locationId: String,
        user:String,
        password: String,
        stateLiveData:StateLiveData<LocGetData>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val locDataGetIpad = getLocDataGetIpadEncryptedEncodedPayload(timeStamp, companyID, locationId, user, password)
        val requestLocDataGetIpad = JsonObject().apply {
            addProperty(L_TIME_KEY, timeStamp)
            addProperty(PAYLOAD_KEY, locDataGetIpad)
        }
        Log.e(TAG, "locDataGetIpad: ${decodeApiResponse("eyJtb25pdG9yX3NlcnZpY2UiOjAsImluZG9vciI6eyJpbmRvb3JfcG0iOjAsImluZG9vcl9jbzIiOjAsImluZG9vcl92b2MiOjAsImluZG9vcl90ZW1wZXJhdHVyZSI6MCwiaW5kb29yX2h1bWlkaXR5IjowLCJpbmRvb3JfdGltZSI6IjE5NzAtMDEtMDEgMDA6MDA6MDAiLCJwYXJhbV9sYWJlbCI6IlBNIDIuNSIsImRpc3BsYXlfcGFyYW0iOjAsIm5hbWVfZW4iOiIiLCJsb24iOiIiLCJsYXQiOiIifSwib3V0ZG9vciI6eyJvdXRkb29yX3BtIjoxNywib3V0ZG9vcl90aW1lIjoiMjAyMS0xMC0xMiAwNjowMDowMCIsIm91dGRvb3JfZGlzcGxheV9wYXJhbSI6MTcsIm91dGRvb3JfbmFtZV9lbiI6Ik1hZHJpZCIsIm5hbWVfZW4iOiJNYWRyaWQiLCJsb24iOiIiLCJsYXQiOiIifSwiRXhwRmlsdGVyIjowLCJFeHBFcXVpcCI6MCwiZW5lcmd5Ijp7Im1vbnRoIjowLCJtYXgiOjAsImN1cnJlbnRfdXNlZCI6MCwiY3VycmVudF9tYXgiOjB9fQ==")}")
        httpRequest(stateLiveData){
            api.locDataGetIpad(pl = requestLocDataGetIpad)
        }
    }
    suspend fun getExtLocInfo(
        companyID: String,
        locationId: String,
        stateLiveData: StateLiveData<GetMonitorLocInfo>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val getExtLocInfo = getExtLocInfoEncryptedEncodedPayload(timeStamp,companyID,locationId)
        val requestGetExtLocInfo = JsonObject().apply {
            addProperty(L_TIME_KEY, timeStamp)
            addProperty(PAYLOAD_KEY, getExtLocInfo)
        }
        httpRequest(stateLiveData){
            api.getExtLocInfo(pl = requestGetExtLocInfo)
        }
    }
    suspend fun getInterfaceDetails(
        dashBoardId:String,
        username:String,
        password:String,
        stateLiveData: StateLiveData<InterfaceDetails>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val getInterfaceDetails = getInterfaceDetailsEncryptedEncodedPayload(timeStamp,dashBoardId,username,password)
        val requestInterfaceDetails = JsonObject().apply {
            addProperty(L_TIME_KEY, timeStamp)
            addProperty(PAYLOAD_KEY, getInterfaceDetails)
        }
        httpRequest(stateLiveData){
            api.getInterfaceDetails(pl = requestInterfaceDetails)
        }
    }
    companion object {
        const val TAG = "HttpsRepo"
    }
}
