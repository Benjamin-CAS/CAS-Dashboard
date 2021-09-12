package com.cas.casdashboard.model.room.entity


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "company_all_entity")
data class CompanyAllEntity(
    @PrimaryKey(autoGenerate = false)
    val companyAllId:Int,
    val companyAllName:String,
    val active:Int,
    val outDoor:Int,
    val secure:Int,
    val lastUpdated: Long = System.currentTimeMillis(),
)