package com.cas.casdashboard.frg

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cas.casdashboard.R
import com.cas.casdashboard.adapter.MonitoringDevicesAdapter
import com.cas.casdashboard.databinding.FragmentMonitoringBinding
import com.cas.casdashboard.https.response.decode.InterfaceDetails
import com.cas.casdashboard.https.util.IStateObserver
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
class MonitoringFragment(private val pageId: String) :BaseFragment<FragmentMonitoringBinding,MonitoringFrgViewModel>(R.layout.fragment_monitoring) {
    private val devicesAdapter = MonitoringDevicesAdapter {

    }
    override val binding: FragmentMonitoringBinding by bindView()
    override val viewModel: MonitoringFrgViewModel by viewModels()
    override fun initView() {
        viewModel.postValueToIsHideProgress(true)
        binding.devicesRv.apply {
            layoutManager = GridLayoutManager(requireContext(),1)
            adapter = devicesAdapter
        }
        viewModel.getAdministrator(Constants.companyName,pageId)
        viewModel.monitoringDeviceData.observe(viewLifecycleOwner,object :IStateObserver<InterfaceDetails>(){
            override fun onLoading() {}
            override fun onDataChange(data: InterfaceDetails) {
                Log.e(TAG, "onDataChange: $data")
                binding.apply {
                    monitoringPmText.text = data.avgAqi.pm.toString()
                    monitoringCo2Text.text = data.avgAqi.co2.toString()
                    monitoringTvocText.text = data.avgAqi.voc
                    monitoringTempText.text = data.avgAqi.temperature.toString()
                    monitoringHumidityText.text = data.avgAqi.humidity.toString()
                }
                devicesAdapter.submitList(data.zones[0].devices.deviceDetails)
                viewModel.postValueToIsHideProgress(false)
            }

            override fun onDataEmpty() {
                viewModel.postValueToIsHideProgress(true)
            }

            override fun onFailed(msg: String) {
                viewModel.postValueToIsHideProgress(true)
            }

            override fun onError(error: Throwable) {
                viewModel.postValueToIsHideProgress(true)
            }

        })
        viewModel.isHideProgress.observe(viewLifecycleOwner){
            binding.apply {
                rvProgress.isVisible = it
                zoneAirProgress.isVisible = it
                devicesRv.isVisible = !it
                monitoringPmText.isVisible = !it
                monitoringCo2Text.isVisible = !it
                monitoringTvocText.isVisible = !it
                monitoringTempText.isVisible = !it
                monitoringHumidityText.isVisible = !it
            }
        }
    }
    companion object {
        const val TAG = "MonitoringFragment"
    }

}