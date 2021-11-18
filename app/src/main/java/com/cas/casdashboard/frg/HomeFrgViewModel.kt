package com.cas.casdashboard.frg

import androidx.lifecycle.ViewModel
import com.cas.casdashboard.https.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.11 11:04
 */
@HiltViewModel
class HomeFrgViewModel  @Inject constructor(private val httpRepo: AppRepo): ViewModel() {
    fun getLoginResultItem() = httpRepo.getLoginResultItem()
}