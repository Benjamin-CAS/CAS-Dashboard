package com.cas.casdashboard.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cas.casdashboard.model.room.entity.Administrator
import kotlinx.coroutines.flow.Flow

/**
 * @author Benjamin
 * @description:
 * @date :20219.18 15:27
 */
@Dao
interface AdministratorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdministrator(administrator: Administrator)
    @Query("SELECT * FROM ADMINISTRATOR WHERE companyName LIKE '%' || :query || '%'")
    suspend fun getAdministrator(query:String):Administrator
}