package com.cas.casdashboard.model.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cas.casdashboard.model.room.entity.CompanyAllEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyAllEntityDao {
    @Query("SELECT * FROM COMPANY_ALL_ENTITY ORDER BY lastUpdated DESC")
    fun getAllCompany(): Flow<List<CompanyAllEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(companyAllEntity: CompanyAllEntity)
    @Query("DELETE FROM COMPANY_ALL_ENTITY")
    suspend fun deleteAllCompanyAllEntity()
    @Query("SELECT * FROM COMPANY_ALL_ENTITY WHERE companyAllName LIKE '%' || :query || '%'")
    fun getSearchCompanyAllName(query:String):Flow<List<CompanyAllEntity>>
}