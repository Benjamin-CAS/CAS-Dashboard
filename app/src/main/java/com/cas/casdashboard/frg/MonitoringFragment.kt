package com.cas.casdashboard.frg

import android.util.Log
import androidx.fragment.app.viewModels
import com.cas.casdashboard.R
import com.cas.casdashboard.databinding.FragmentMonitoringBinding
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants
import com.cas.casdashboard.util.bindView
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.24 11:31
 */
@AndroidEntryPoint
class MonitoringFragment(private val pageId: String) :BaseFragment<FragmentMonitoringBinding>(R.layout.fragment_monitoring) {
    private val monitoringFrgViewModel by viewModels<MonitoringFrgViewModel>()
    override val binding: FragmentMonitoringBinding by bindView()

    override fun initView() {
        Log.e(TAG, "initView: $pageId")
        monitoringFrgViewModel.getMonitorLocInfo().observe(viewLifecycleOwner){
            Log.e(TAG, "initView: $it")
            binding.apply {
                monitoringPmText.text = it.pm
                monitoringCo2Text.text = it.co2
                monitoringTvocText.text = it.tvoc
                monitoringTempText.text = it.temperature
                monitoringHumidityText.text = it.humidity
            }
        }
        monitoringFrgViewModel.getAdministrator(Constants.companyName,pageId)
    }
    companion object{
        const val TAG = "MonitoringFragment"
    }
}