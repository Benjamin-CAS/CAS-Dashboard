package com.cas.casdashboard.frg

import androidx.lifecycle.ViewModel
import com.cas.casdashboard.https.repo.ApiRepo
import com.cas.casdashboard.https.repo.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CMSViewModel @Inject constructor(private val appRepo: AppRepo, private val apiRepo: ApiRepo):ViewModel() {

}