package com.cas.casdashboard.frg

import androidx.fragment.app.viewModels
import com.cas.casdashboard.R
import com.cas.casdashboard.databinding.FragmentCmsBinding
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.X5Util
import com.cas.casdashboard.util.bindView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CMSFragment:BaseFragment<FragmentCmsBinding,CMSViewModel>(R.layout.fragment_cms) {
    override val viewModel: CMSViewModel by viewModels()
    override val binding: FragmentCmsBinding by bindView()

    override fun initView() {
        viewBindingApply()
    }
    private fun viewBindingApply() = with(binding){
        webview.apply {
            X5Util.loadX5(this)
            loadUrl("https://cms.cleanairspaces.com/index.php/cms")
        }
    }
}