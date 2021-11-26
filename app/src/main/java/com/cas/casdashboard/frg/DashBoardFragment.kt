package com.cas.casdashboard.frg


import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.cas.casdashboard.R
import com.cas.casdashboard.databinding.FragmentDashboardBinding
import com.cas.casdashboard.https.response.decode.GetMonitorLocInfo
import com.cas.casdashboard.https.response.decode.LocGetData
import com.cas.casdashboard.https.util.IStateObserver
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants
import com.cas.casdashboard.util.Constants.BACKGROUND
import com.cas.casdashboard.util.Constants.LOGO
import com.cas.casdashboard.util.bindView
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashBoardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard){
    private val dashBoardFrgViewModel by viewModels<DashBoardFrgViewModel>()
    private val mk: MMKV = MMKV.defaultMMKV()
    override val binding by bindView<FragmentDashboardBinding>()
    override fun initView() {
        Constants.isLockedMode.postValue(mk.decodeBool("IS_LOCKED_MODE"))
        dashBoardFrgViewModel.getAdministrator(Constants.companyName)
        dashBoardFrgViewModel.locDataGetIpad.observe(viewLifecycleOwner,object : IStateObserver<LocGetData>(){
            override fun onDataChange(data: LocGetData) {
                Log.e(HomeFragment.TAG, "onDataChange: $data")
                binding.apply {
                    progressIndoor.isVisible = false
                    progressOutdoor.isVisible = false
                    temperatureText.isVisible = true
                    percentText.isVisible = true
                    indoorProgressBar.isVisible = true
                    indoorCompanyNameText.isVisible = true
                    indoorTime.isVisible = true
                    indoorProgressBar.setNumTextWithProgressSweepAngle(data.indoor.displayParam.toString())
                    indoorProgressBar.setLabelText(data.indoor.paramLabel)
                    indoorCompanyNameText.text = data.indoor.nameEn
                    temperatureText.text = data.indoor.indoorTemperature.toString().plus("℃")
                    percentText.text = data.indoor.indoorHumidity.toString().plus("%")
                    indoorTime.text = data.indoor.indoorTime
                    pmText.text = data.indoor.indoorPm.toString().plus(getString(R.string.unit_pm))
                    vocText.text = data.indoor.indoorVoc.plus(getString(R.string.unit_tvoc))
                    co2Text.text = data.indoor.indoorCo2.toString().plus(getString(R.string.unit_co2))
                }
                binding.apply {
                    outdoorCompanyText.text = data.outdoor.outdoorNameEn
                    outdoorTime.text = data.outdoor.outdoorTime
                    outdoorProgressBar.isVisible = true
                    outdoorProgressBar.setNumTextWithProgressSweepAngle(data.outdoor.outdoorDisplayParam.toString())
                }
            }

            override fun onDataEmpty() {

            }

            override fun onFailed(msg: String) {

            }

            override fun onError(error: Throwable) {

            }

            override fun onLoading() {
                binding.apply {
                    progressIndoor.isVisible = true
                    progressOutdoor.isVisible = true
                    indoorProgressBar.isVisible = false
                    temperatureText.text = ""
                    percentText.text = ""
                    indoorTime.text = ""
                    pmText.text = ""
                    vocText.text = ""
                    co2Text.text = ""
                    indoorCompanyNameText.text = ""
                }
                binding.apply {
                    outdoorProgressBar.isVisible = false
                    outdoorTime.text = ""
                    outdoorCompanyText.text = ""
                }
            }

        })
        dashBoardFrgViewModel.getMonitorLocInfo.observe(viewLifecycleOwner,object : IStateObserver<GetMonitorLocInfo>(){
            override fun onDataChange(data: GetMonitorLocInfo) {
                Log.e(TAG, "onDataChange: $data")
                binding.apply {
                    backgroundImage.load(data[0].picture.BACKGROUND)
                    companyLogo.load(data[0].logo.LOGO)
                }
                dashBoardFrgViewModel.insertGetMonitorLocInfo(data)
            }
            override fun onDataEmpty() {}
            override fun onFailed(msg: String) {}
            override fun onError(error: Throwable) {}
            override fun onLoading() {}
        })
    }
    companion object{
        const val TAG = "DashBoardFragment"
    }
}