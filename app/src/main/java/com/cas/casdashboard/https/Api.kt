package com.cas.casdashboard.https

import com.cas.casdashboard.https.response.CompanyAll
import com.cas.casdashboard.util.Constants.API_APP_ID
import com.cas.casdashboard.util.Constants.NONCE
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface Api{
    companion object {
        const val COM_ALL = "ComAll"
        const val LOCATION_INFO_METHOD_FOR_KEY = "GetDevInfoByIdCAS"
        const val LOC_GET_IPAD = "LocGetIpad"
    }
    @POST("index.php/api/router")
    suspend fun getAllCompany(
        @Query("app_id") app_id:Int = API_APP_ID,
        @Query("method") method:String = COM_ALL,
        @Query("nonce") nonce:String = NONCE,
        @Body payload:String
    ): CompanyAll
    @POST("index.php/api/approuter")
    suspend fun getCompanyLocation(
        @Query("app_id") app_id:Int = API_APP_ID,
        @Query("method") method:String = LOC_GET_IPAD,
        @Query("nonce") nonce:String = NONCE,
        @Body payload: JsonObject
    )
}