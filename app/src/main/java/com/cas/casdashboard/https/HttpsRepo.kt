package com.cas.casdashboard.https

import android.util.Log
import androidx.lifecycle.asLiveData
import com.cas.casdashboard.model.room.dao.CompanyAllEntityDao
import com.cas.casdashboard.model.room.entity.CompanyAllEntity
import com.cas.casdashboard.util.Constants.getEncryptedEncodedPayloadForIndoorLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpsRepo @Inject constructor(private val api: Api,private val companyAllEntityDao: CompanyAllEntityDao) {
    suspend fun getAllCompany(){
        Log.e(TAG, "getAllCompany: 执行了")
        val timeStamp = System.currentTimeMillis().toString()
        val allCompanyResult = api.getAllCompany(payload = getEncryptedEncodedPayloadForIndoorLocation(timeStamp))
        companyAllEntityDao.deleteAllCompanyAllEntity()
        for (indoorLocation in allCompanyResult.data)
            companyAllEntityDao.insertCompany(CompanyAllEntity(
                companyAllId = indoorLocation.company_id,
                companyAllName = indoorLocation.name_en,
                active = indoorLocation.active,
                outDoor = indoorLocation.outdoor,
                secure = indoorLocation.secure
            ))

    }
    fun observeAllCompany() = companyAllEntityDao.getAllCompany().asLiveData()
    fun getSearchCompanyAllName(query:String) = companyAllEntityDao.getSearchCompanyAllName(query)


    companion object {
        const val TAG = "HttpsRepo"
    }
}