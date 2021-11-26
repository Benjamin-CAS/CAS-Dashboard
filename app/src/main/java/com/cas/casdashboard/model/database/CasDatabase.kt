package com.cas.casdashboard.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cas.casdashboard.https.response.decode.GetMonitorLocInfo
import com.cas.casdashboard.https.response.decode.LoginResultItem
import com.cas.casdashboard.model.room.dao.AdministratorDao
import com.cas.casdashboard.model.room.dao.CompanyAllEntityDao
import com.cas.casdashboard.model.room.dao.GetMonitorLocInfoDao
import com.cas.casdashboard.model.room.dao.LoginResultItemDao
import com.cas.casdashboard.model.room.entity.AccountInformation
import com.cas.casdashboard.model.room.entity.Administrator
import com.cas.casdashboard.model.room.entity.CompanyAllEntity
import com.cas.casdashboard.model.room.entity.GetMonitorLocInfoItemEntity

@Database(
    entities = [CompanyAllEntity::class, Administrator::class, AccountInformation::class, LoginResultItem::class, GetMonitorLocInfoItemEntity::class],version = 1,exportSchema = false
)
abstract class CasDatabase: RoomDatabase() {
    abstract fun companyAllEntityDao(): CompanyAllEntityDao
    abstract fun administratorDao():AdministratorDao
    abstract fun loginResultItemDao(): LoginResultItemDao
    abstract fun getMonitorLocInfoDao(): GetMonitorLocInfoDao
}