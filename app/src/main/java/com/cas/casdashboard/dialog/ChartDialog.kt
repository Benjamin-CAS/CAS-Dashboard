package com.cas.casdashboard.dialog

import com.cas.casdashboard.R
import com.cas.casdashboard.databinding.ChartDialogBinding
import com.cas.casdashboard.util.BaseDialogFragment
import com.cas.casdashboard.util.bindView

/**
 * @author Benjamin
 * @description:
 * @date :2021.10.9 14:09
 */
class ChartDialog:BaseDialogFragment<ChartDialogBinding>(R.layout.chart_dialog) {
    override val binding: ChartDialogBinding by bindView()
    override val mWidth: Float = 0.7F
    override val mHeight: Float = 0.8F
    override fun initView() {

    }

}