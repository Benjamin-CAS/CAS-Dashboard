package com.cas.casdashboard.util

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB:ViewBinding,VM:ViewModel>(@LayoutRes contentLayoutId:Int):Fragment(contentLayoutId) {
    protected abstract val binding:VB
    protected abstract fun initView()
    protected abstract val viewModel:VM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
}