package com.cas.casdashboard.https.repo

import android.util.Log
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
        Log.e(AppRepo.TAG, "locDataGetIpad: ${CasEncDecPayload.decodeApiResponse("eyJtb25pdG9yX3NlcnZpY2UiOjAsImluZG9vciI6eyJpbmRvb3JfcG0iOjAsImluZG9vcl9jbzIiOjAsImluZG9vcl92b2MiOjAsImluZG9vcl90ZW1wZXJhdHVyZSI6MCwiaW5kb29yX2h1bWlkaXR5IjowLCJpbmRvb3JfdGltZSI6IjE5NzAtMDEtMDEgMDA6MDA6MDAiLCJwYXJhbV9sYWJlbCI6IlBNIDIuNSIsImRpc3BsYXlfcGFyYW0iOjAsIm5hbWVfZW4iOiIiLCJsb24iOiIiLCJsYXQiOiIifSwib3V0ZG9vciI6eyJvdXRkb29yX3BtIjoxNywib3V0ZG9vcl90aW1lIjoiMjAyMS0xMC0xMiAwNjowMDowMCIsIm91dGRvb3JfZGlzcGxheV9wYXJhbSI6MTcsIm91dGRvb3JfbmFtZV9lbiI6Ik1hZHJpZCIsIm5hbWVfZW4iOiJNYWRyaWQiLCJsb24iOiIiLCJsYXQiOiIifSwiRXhwRmlsdGVyIjowLCJFeHBFcXVpcCI6MCwiZW5lcmd5Ijp7Im1vbnRoIjowLCJtYXgiOjAsImN1cnJlbnRfdXNlZCI6MCwiY3VycmVudF9tYXgiOjB9fQ==")}")
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