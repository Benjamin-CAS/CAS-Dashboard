package com.cas.casdashboard.util

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

/**
 * @author Benjamin
 * @description:
 * @date :2021.10.9 14:06
 */
abstract class BaseDialogFragment<VB: ViewBinding>(@LayoutRes contentLayoutId:Int): DialogFragment(contentLayoutId) {
    protected abstract val binding:VB
    protected abstract fun initView()
    protected abstract val mWidth:Float
    protected abstract val mHeight:Float
    override fun onStart() {
        super.onStart()
        val mWindow = dialog?.window
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        mWindow?.apply {
            setLayout((width * mWidth).toInt(), (height * mHeight).toInt())
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
}