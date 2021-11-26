package com.cas.casdashboard.frg

import androidx.lifecycle.ViewModel
import com.cas.casdashboard.https.repo.AppRepo
import com.tencent.mmkv.MMKV
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.11 11:04
 */
@HiltViewModel
class HomeFrgViewModel  @Inject constructor(private val httpRepo: AppRepo): ViewModel() {
    private val mk: MMKV = MMKV.defaultMMKV()
    fun getLoginResultItem() = httpRepo.getLoginResultItem()
}