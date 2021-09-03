package com.cas.casdashboard.frg

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB:ViewBinding>(@LayoutRes contentLayoutId:Int):Fragment(contentLayoutId) {
    protected abstract val binding:VB
    protected abstract fun initView()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
}