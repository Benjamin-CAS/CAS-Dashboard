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
    companion object {
        const val TAG = "HttpsRepo"
    }
}
