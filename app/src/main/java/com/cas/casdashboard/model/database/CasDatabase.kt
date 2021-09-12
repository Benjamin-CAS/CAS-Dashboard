package com.cas.casdashboard.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cas.casdashboard.model.room.dao.CompanyAllEntityDao
import com.cas.casdashboard.model.room.entity.CompanyAllEntity

@Database(
    entities = [CompanyAllEntity::class],version = 1,exportSchema = false
)
abstract class CasDatabase: RoomDatabase() {
    abstract fun companyAllEntityDao(): CompanyAllEntityDao
}