package com.cas.casdashboard.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.18 16:45
 */
@Entity(tableName = "account_information")
data class AccountInformation(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val bgImage:String,
    val name:String,
    val type:String,
    val logo:String,
    val isLockedMode:Boolean,
    val lastUpdated: Long = System.currentTimeMillis()
)