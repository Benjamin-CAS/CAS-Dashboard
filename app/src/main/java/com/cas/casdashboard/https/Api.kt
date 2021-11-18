package com.cas.casdashboard.https

import com.cas.casdashboard.https.response.CompanyAll
import com.cas.casdashboard.https.response.encode.*
import com.cas.casdashboard.util.Constants.API_APP_ID
import com.cas.casdashboard.util.Constants.NONCE
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface Api{
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
        @Body pl: JsonObject
    ): CompanyLocation
    @POST("/index.php/api/approuter")
    suspend fun getLogin(
        @Query("app_id") app_id:Int = API_APP_ID,
        @Query("method") method:String = GET_LOGIN_INTERFACE,
        @Query("nonce") nonce:String = NONCE,
        @Body pl:JsonObject
    ): Encode
    @POST("/index.php/api/approuter")
    suspend fun locDataGetIpad(
        @Query("app_id") app_id:Int = API_APP_ID,
        @Query("method") method:String = LOC_DATA_GET_IPAD,
        @Query("nonce") nonce:String = NONCE,
        @Body pl:JsonObject
    ): Encode
    @POST("/index.php/api/approuter")
    suspend fun getExtLocInfo(
        @Query("app_id") app_id:Int = API_APP_ID,
        @Query("method") method:String = GET_EXT_LOC_INFO,
        @Query("nonce") nonce:String = NONCE,
        @Body pl:JsonObject
    ): Encode
    companion object {
        const val COM_ALL = "ComAll"
        const val LOCATION_INFO_METHOD_FOR_KEY = "GetDevInfoByIdCAS"
        const val LOC_GET_IPAD = "LocGetIpad"
        const val LOC_GET_IPAD_FOR_KEY = "LocGetIpadCAS"
        const val GET_LOGIN_INTERFACE = "GetInterface"
        const val GET_LOGIN_INTERFACE_FOR_KET = "GetInterfaceCAS"
        const val GET_INTERFACE_DETAILS = "GetInterfaceDetails"
        const val LOC_DATA_GET_IPAD = "LocDataGetIpad"
        const val LOC_DATA_GET_IPAD_KEY = "LocDataGetIpadCAS"
        const val GET_EXT_LOC_INFO = "GetExtLocInfo"
        const val GET_EXT_LOC_INFO_KEY = "GetExtLocInfoCAS"
    }
}