package com.cas.casdashboard.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Benjamin
 * @description:
 * @date :2021..9.16 17:26
 */
@Entity(tableName = "administrator")
data class Administrator(
    @PrimaryKey(autoGenerate = false)
    val companyName:String,
    val companyId:String,
    val locationName:String,
    val locationId:String,
    val username:String,
    val password:String
)
