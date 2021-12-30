package com.cas.casdashboard.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cas.casdashboard.databinding.MonitoringDeviceStatusItemBinding
import com.cas.casdashboard.https.response.decode.DeviceDetail

/**
 * @author Benjamin
 * @description:
 * @date :2021.11.29 10:30
 */
class MonitoringDevicesAdapter(private val itemClick:(DeviceDetail) -> Unit): ListAdapter<DeviceDetail,MonitoringDevicesAdapter.ViewHolder>(
    object :DiffUtil.ItemCallback<DeviceDetail>(){
        override fun areItemsTheSame(oldItem: DeviceDetail, newItem: DeviceDetail) = oldItem === newItem
        override fun areContentsTheSame(oldItem: DeviceDetail, newItem: DeviceDetail) = oldItem == newItem
    }
) {
    inner class ViewHolder(binding:MonitoringDeviceStatusItemBinding):RecyclerView.ViewHolder(binding.root){
        val root = binding.root
        private val devicesStatus = binding.monitoringDeviceStatus
        private val deviceName = binding.monitoringDeviceName
        private val deviceType = binding.monitoringDeviceType
        private val deviceUv = binding.monitoringDeviceDfUv
        private val deviceMode = binding.monitoringDeviceMode
        private val deviceFan = binding.monitoringDeviceFan
        private val deviceMac = binding.monitoringDeviceMac
        private val deviceStatusBgc = binding.deviceStatusBgc
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(deviceDetail: DeviceDetail){
            deviceDetail.let {
                deviceStatusBgc.setCardBackgroundColor(it.getTextWithBackground().bgc)
                devicesStatus.text = it.getTextWithBackground().statusTxt
                deviceName.text = it.devName
                deviceType.text = it.deviceType
                deviceUv.text = it.uv
                deviceMode.text = it.mode
                deviceFan.text = it.fanSpeed
                deviceMac.text = it.mac
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(MonitoringDeviceStatusItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)).apply {
            root.setOnClickListener {
                itemClick(getItem(absoluteAdapterPosition))
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}