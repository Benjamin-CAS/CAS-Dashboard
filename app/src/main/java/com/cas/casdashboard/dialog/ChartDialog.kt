package com.cas.casdashboard.dialog

import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.cas.casdashboard.R
import com.cas.casdashboard.databinding.ChartDialogBinding
import com.cas.casdashboard.util.BaseDialogFragment
import com.cas.casdashboard.util.bindView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Benjamin
 * @description:
 * @date :2021.10.9 14:09
 */
class ChartDialog:BaseDialogFragment<ChartDialogBinding>(R.layout.chart_dialog) {
    private val goodColor by lazy { ContextCompat.getColor(requireContext(), R.color.aqi_good) }
    private val moderateColor by lazy { ContextCompat.getColor(requireContext(), R.color.aqi_moderate) }
    private val gUnhealthyColor by lazy { ContextCompat.getColor(requireContext(), R.color.aqi_g_unhealthy) }
    private val unhealthyColor by lazy { ContextCompat.getColor(requireContext(), R.color.aqi_unhealthy) }
    private val blackColor by lazy { ContextCompat.getColor(requireContext(), R.color.black) }
    private val titleFont by lazy { ResourcesCompat.getFont(requireContext(), R.font.noto_sans) }
    private val bodyFont by lazy { ResourcesCompat.getFont(requireContext(), R.font.fira_sans) }
    override val binding: ChartDialogBinding by bindView()
    override val mWidth: Float = 0.7F
    override val mHeight: Float = 0.8F
    override fun initView() {
        binding.daysChart.apply {
            axisRight.isEnabled = false
            axisLeft.isEnabled = true
            tag = "barChartTag"
            val legendGood = LegendEntry()
            legendGood.label = getString(R.string.good_air_status_txt)
            legendGood.formColor = goodColor
            val legendModerate = LegendEntry()
            legendModerate.label = getString(R.string.moderate_air_status_txt)
            legendModerate.formColor = moderateColor
            val legendSBad = LegendEntry()
            legendSBad.label = getString(R.string.aqi_status_unhealthy_sensitive_groups_abbrev)
            legendSBad.formColor = gUnhealthyColor
            val legendBad = LegendEntry()
            legendBad.label = getString(R.string.danger_txt)
            legendBad.formColor = unhealthyColor
            xAxis.apply {
                labelRotationAngle = 0f
                setDrawGridLines(false)
                position = XAxis.XAxisPosition.BOTTOM
                typeface = titleFont
                textSize = 12f
                axisMinimum = 0f
                setDrawLabels(false)
                setDrawAxisLine(true)
            }
            legend.apply {
                typeface = bodyFont
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                yOffset = 0f
                setCustom(listOf(legendGood, legendModerate, legendSBad, legendBad))
            }
            setNoDataTextTypeface(titleFont)
            setTouchEnabled(true)  // 是否禁止触摸
            setPinchZoom(false)   // 缩放
            isDoubleTapToZoomEnabled = false
            description.isEnabled = false
            setNoDataText(getString(R.string.loading_graph_data))
            setNoDataTextTypeface(bodyFont)
            setNoDataTextColor(blackColor)
            animateX(1000, Easing.EaseInExpo)
            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    e?.let { entry ->
                        Log.e(TAG, "onValueSelected: $entry")
                    }
                }

                override fun onNothingSelected() {

                }
            })
        }
        click()
    }
    private fun click(){
        val lists = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val barLists = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val entity = ArrayList<Entry>()
        val barEntity = ArrayList<BarEntry>()
        for ((i,item) in lists.withIndex()){
            entity.add(Entry(i.toFloat(), item.toFloat()))
        }
        for ((i,item) in barLists.withIndex()){
            barEntity.add(BarEntry(i.toFloat(),item.toFloat()))
        }
        val dataSetLine = LineDataSet(entity,"数据").apply {
            color = Color.BLUE // 线条颜色
            formSize = 10f
            setDrawValues(false)
            valueTextSize = 12f
            valueTextColor = blackColor // 折现圆圈值颜色
            valueTypeface = titleFont
            setDrawCircleHole(false)
            lineWidth = 1f
            circleRadius = 2.5f
        }

        val dataSetLineBar = BarDataSet(barEntity,"bar").apply {
            colors = listOf(Color.BLACK,Color.GREEN)
            formSize = 10f
            setDrawValues(false)
            valueTextSize = 12f
            valueTextColor = blackColor
            valueTypeface = titleFont
        }
        binding.daysChart.apply {
            val combinedData = CombinedData()
            val lineData = LineData(dataSetLine)
            val barData = BarData(dataSetLineBar)
            combinedData.setData(lineData)
            combinedData.setData(barData)
            data = combinedData
        }
    }
    private fun styleBarDataSet(barDataSet: BarDataSet, valColorMap: ArrayList<Int>) {
        barDataSet.apply {
            colors = valColorMap
            formSize = 10f
            setDrawValues(false)
            valueTextSize = 12f
            valueTextColor = blackColor
            valueTypeface = titleFont
        }
    }
    companion object{
        const val TAG = "ChartDialog"
    }
}