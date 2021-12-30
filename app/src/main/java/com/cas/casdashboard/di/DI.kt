package com.cas.casdashboard.di

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import com.cas.casdashboard.https.Api
import com.cas.casdashboard.https.util.ParamsLogInterceptor
import com.cas.casdashboard.model.database.CasDatabase
import com.cas.casdashboard.model.room.dao.AdministratorDao
import com.cas.casdashboard.model.room.dao.CompanyAllEntityDao
import com.cas.casdashboard.model.room.dao.GetMonitorLocInfoDao
import com.cas.casdashboard.model.room.dao.LoginResultItemDao
import com.cas.casdashboard.util.LogUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .writeTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .addInterceptor(ParamsLogInterceptor())
                    .addInterceptor(HttpLoggingInterceptor{
                        LogUtil.e(TAG, "provideRetrofit: $it")
                    }.apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
//            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()

    @Provides
    @Singleton
    fun getAllCompany(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        app:Application
    ): CasDatabase = Room.databaseBuilder(app, CasDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
    @Provides
    @Singleton
    fun provideCompanyAllEntityDao(casDatabase: CasDatabase): CompanyAllEntityDao =
        casDatabase.companyAllEntityDao()
    @Provides
    @Singleton
    fun provideAdministrator(casDatabase: CasDatabase):AdministratorDao = casDatabase.administratorDao()
    @Provides
    @Singleton
    fun provideLoginResultItemDao(casDatabase: CasDatabase): LoginResultItemDao = casDatabase.loginResultItemDao()
    @Provides
    @Singleton
    fun provideGetMonitorLocInfoDao(casDatabase: CasDatabase):GetMonitorLocInfoDao = casDatabase.getMonitorLocInfoDao()
    @Provides
    @Singleton
    fun provideWorkManager(app: Application): WorkManager =
        WorkManager.getInstance(app.applicationContext)
    private const val TAG = "DI"
    private const val BASE_URL = "https://monitor.cleanairspaces.com/"
    private const val DATABASE_NAME = "CAS_DATABASE"
}