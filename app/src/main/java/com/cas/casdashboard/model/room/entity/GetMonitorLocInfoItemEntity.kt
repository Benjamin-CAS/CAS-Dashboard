package com.cas.casdashboard.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.26 12:09
 */
@Entity(tableName = "GET_MONITOR_LOC_INFO")
data class GetMonitorLocInfoItemEntity(
    @PrimaryKey(autoGenerate = false)
    val co2: String,
    val humidity: String,
    val lat: String,
    val locationId: Int,
    val logo: String,
    val lon: String,
    val picture: String,
    val pm: String,
    val temperature: String,
    val tvoc: String
)
