package com.cas.casdashboard.frg

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.cas.casdashboard.R
import com.cas.casdashboard.adapter.MonitoringDevicesAdapter
import com.cas.casdashboard.adapter.SelectZoneAdapter
import com.cas.casdashboard.databinding.FragmentMonitoringBinding
import com.cas.casdashboard.https.response.decode.InterfaceDetails
import com.cas.casdashboard.https.util.IStateObserver
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants
import com.cas.casdashboard.util.LogUtil
import com.cas.casdashboard.util.bindView
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.24 11:31
 */
@AndroidEntryPoint
class MonitoringFragment(private val pageId: String) :BaseFragment<FragmentMonitoringBinding,MonitoringFrgViewModel>(R.layout.fragment_monitoring) {
    override val binding: FragmentMonitoringBinding by bindView()
    override val viewModel: MonitoringFrgViewModel by viewModels()
    private lateinit var devicesList:InterfaceDetails
    override fun initView() {
        viewBindingApply()
        requestViewModelApi()
        viewModelValueObserve()
        viewModel.postValueToIsHideProgress(true)
        viewModel.postValueToIsHideSelectRv(false)
    }
    private val selectZoneAdapter = SelectZoneAdapter { zone, _ ->
        viewModel.postValueToIsHideSelectRv(false)
        binding.selectZoneText.text = zone.nameEn
        if (zone.nameEn == "All") {
            val devices = devicesList.getAllDevices()
            devicesAdapter.submitList(devices)
            binding.totalDeviceNum.text = devices.size.toString()
        } else {
            devicesAdapter.submitList(zone.devices?.deviceDetails)
        }
    }
    private val devicesAdapter = MonitoringDevicesAdapter {

    }
    private fun viewBindingApply() = with(binding){
        root.setOnClickListener {
            viewModel.postValueToIsHideSelectRv(false)
        }
        devicesRv.apply {
            layoutManager = GridLayoutManager(requireContext(),1)
            adapter = devicesAdapter
        }
        selectZoneRv.apply {
            layoutManager = GridLayoutManager(requireContext(),1)
            adapter = selectZoneAdapter
        }
        selectZoneRv.setOnClickListener {
            viewModel.postValueToIsHideSelectRv(true)
        }
        selectZoneText.setOnClickListener {
            viewModel.postValueToIsHideSelectRv(true)
        }
        if (Constants.companyLogo.isNotBlank())
            binding.monitoringLogo.load(Constants.companyLogo)
    }
    private fun requestViewModelApi(){
        viewModel.getAdministrator(Constants.companyName,pageId)
        viewModel.monitoringDeviceData.observe(viewLifecycleOwner,object :IStateObserver<InterfaceDetails>(){
            override fun onLoading() {}
            override fun onDataChange(data: InterfaceDetails) {
                LogUtil.e(TAG, "onDataChange: $data")
                binding.apply {
                    monitoringPmText.text = data.avgAqi.pm
                    monitoringCo2Text.text = data.avgAqi.co2
                    monitoringTvocText.text = data.avgAqi.voc
                    monitoringTempText.text = data.avgAqi.temperature
                    monitoringHumidityText.text = data.avgAqi.humidity
                    issuesDeviceNum.text = data.getFaultDevicesNum().size.toString()
                    deviceAvailabilityNum.text = data.getAllNormalDevicesNum().toString().plus("％")
                    energyConsumptionNum.text = data.getAllOnDevicesNum().size.toString().plus("％")
                }
                selectZoneAdapter.submitList(data.getAllZones()){
                    devicesList = data
                    selectZoneAdapter.setPosition()
                }
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
    }
    private fun viewModelValueObserve(){
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
        viewModel.isHideSelectRv.observe(viewLifecycleOwner){
            binding.selectZoneRv.isVisible = it
        }
    }
    companion object {
        const val TAG = "MonitoringFragment"
    }

}