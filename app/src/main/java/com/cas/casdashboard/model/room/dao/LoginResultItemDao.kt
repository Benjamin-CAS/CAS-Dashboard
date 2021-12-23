package com.cas.casdashboard.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cas.casdashboard.https.response.decode.LoginResultItem
import com.cas.casdashboard.model.room.entity.AccountInformation
import kotlinx.coroutines.flow.Flow

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.18 16:47
 */
@Dao
interface LoginResultItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginResultItem(loginResultItem: List<LoginResultItem>)
    @Query("SELECT * FROM LOGIN_RESULT_ITEM ORDER BY lastUpdated DESC")
    fun getAllLoginResultItem():Flow<List<LoginResultItem>>
    @Query("DELETE FROM LOGIN_RESULT_ITEM")
    suspend fun deleteAllLoginResultItem()

}