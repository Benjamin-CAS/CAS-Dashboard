package com.cas.casdashboard.https.repo

import com.cas.casdashboard.https.Api
import com.cas.casdashboard.https.response.decode.*
import com.cas.casdashboard.https.util.StateLiveData
import com.cas.casdashboard.https.util.httpRequest
import com.cas.casdashboard.util.CasEncDecPayload
import com.cas.casdashboard.util.Constants
import com.google.gson.JsonObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepo @Inject constructor(
    private val api:Api
) {
    suspend fun getCompanyLocation(companyID: String, stateLiveData: StateLiveData<CompanyLocationDecode>){
        val timeStamp = System.currentTimeMillis().toString()
        val locationEncryptedEncodedPayload =
            CasEncDecPayload.getLocationEncryptedEncodedPayload(timeStamp, companyID)
        val requestGetCompanyLocationData = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, locationEncryptedEncodedPayload)
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
            CasEncDecPayload.getLoginEncryptedEncodedPayload(
                timeStamp,
                locationId,
                username,
                password
            )
        val requestGetLoginData = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, loginEncryptedEncodedPayload)
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
        stateLiveData: StateLiveData<LocGetData>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val locDataGetIpad = CasEncDecPayload.getLocDataGetIpadEncryptedEncodedPayload(
            timeStamp,
            companyID,
            locationId,
            user,
            password
        )
        val requestLocDataGetIpad = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, locDataGetIpad)
        }
        httpRequest(stateLiveData){
            api.locDataGetIpad(pl = requestLocDataGetIpad)
        }
    }
    suspend fun locDataGetIpadLastMonthHistory(
        companyID: String,
        locationId: String,
        user:String,
        password: String,
        stateLiveData: StateLiveData<HistoryLastMonthData>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val locDataGetIpadHistory = CasEncDecPayload.getLocDataGetIpadHistoryEncryptedEncodedPayload(
            timeStamp,
            companyID,
            locationId,
            user,
            password,
            CasEncDecPayload.DateType.LAST_MONTH
        )
        val requestLocDataGetIpadHistory = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, locDataGetIpadHistory)
        }
        httpRequest(stateLiveData){
            api.locDataGetIpad(pl = requestLocDataGetIpadHistory)
        }
    }
    suspend fun locDataGetIpadLastWeekHistory(
        companyID: String,
        locationId: String,
        user:String,
        password: String,
        stateLiveData: StateLiveData<HistoryLastWeekData>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val locDataGetIpadHistory = CasEncDecPayload.getLocDataGetIpadHistoryEncryptedEncodedPayload(
            timeStamp,
            companyID,
            locationId,
            user,
            password,
            CasEncDecPayload.DateType.LAST_WEEK
        )
        val requestLocDataGetIpadHistory = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, locDataGetIpadHistory)
        }
        httpRequest(stateLiveData){
            api.locDataGetIpad(pl = requestLocDataGetIpadHistory)
        }
    }
    suspend fun locDataGetIpadLast72HHistory(
        companyID: String,
        locationId: String,
        user:String,
        password: String,
        stateLiveData: StateLiveData<HistoryLast72HData>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val locDataGetIpadHistory = CasEncDecPayload.getLocDataGetIpadHistoryEncryptedEncodedPayload(
            timeStamp,
            companyID,
            locationId,
            user,
            password,
            CasEncDecPayload.DateType.LAST_THREE_DAY
        )
        val requestLocDataGetIpadHistory = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, locDataGetIpadHistory)
        }
        httpRequest(stateLiveData){
            api.locDataGetIpad(pl = requestLocDataGetIpadHistory)
        }
    }
    suspend fun getExtLocInfo(
        companyID: String,
        locationId: String,
        stateLiveData: StateLiveData<GetMonitorLocInfo>
    ){
        val timeStamp = System.currentTimeMillis().toString()
        val getExtLocInfo =
            CasEncDecPayload.getExtLocInfoEncryptedEncodedPayload(timeStamp, companyID, locationId)
        val requestGetExtLocInfo = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, getExtLocInfo)
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
        val getInterfaceDetails = CasEncDecPayload.getInterfaceDetailsEncryptedEncodedPayload(
            timeStamp,
            dashBoardId,
            username,
            password
        )
        val requestInterfaceDetails = JsonObject().apply {
            addProperty(Constants.L_TIME_KEY, timeStamp)
            addProperty(Constants.PAYLOAD_KEY, getInterfaceDetails)
        }
        httpRequest(stateLiveData){
            api.getInterfaceDetails(pl = requestInterfaceDetails)
        }
    }
}