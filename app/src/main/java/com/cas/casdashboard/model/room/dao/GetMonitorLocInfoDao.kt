package com.cas.casdashboard.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cas.casdashboard.model.room.entity.GetMonitorLocInfoItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.26 11:05
 */
@Dao
interface GetMonitorLocInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonitorLocInfo(monitorLocInfo: GetMonitorLocInfoItemEntity)
    @Query("SELECT * FROM GET_MONITOR_LOC_INFO")
    fun getAllMonitorLocInfo():Flow<GetMonitorLocInfoItemEntity>
}