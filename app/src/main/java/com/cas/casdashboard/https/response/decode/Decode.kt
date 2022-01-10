package com.cas.casdashboard.https.response.decode

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


class CompanyLocationDecode : ArrayList<CompanyLocationDecodeItem>()
data class CompanyLocationDecodeItem(
    val active: String,
    val location_id: String,
    val logo: String,
    val name_en: String,
    val outdoor: String
)


//data class LoginResult(
//    override val status: Boolean,
//    override val code: Int,
//    override val payload: String,
//    override val msg: String,
//    val data:List<LoginResultItem> = listOf(),
//) :BaseJson()

@Entity(tableName = "login_result_item")
data class LoginResultItem(
    val bgImage: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val type: String,
    val lastUpdated: Long = System.currentTimeMillis()
)


class GetMonitorLocInfo: ArrayList<GetMonitorLocInfoItem>()
data class GetMonitorLocInfoItem(
    @SerializedName("co2")
    val co2: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("location_id")
    val locationId: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("pm")
    val pm: String,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("tvoc")
    val tvoc: String,
)


data class InterfaceDetails(
    @SerializedName("avg_aqi")
    val avgAqi: AvgAqi,
    @SerializedName("energy")
    val energy: Int,
    @SerializedName("zones")
    val zones: List<Zone>
){
    fun getAllDevices(): ArrayList<DeviceDetail> {
        val deviceDetail = ArrayList<List<DeviceDetail>>()
        deviceDetail.clear()
        for (item in zones){
            item.devices?.deviceDetails?.let { deviceDetail.add(it) }
        }
        val device = ArrayList<DeviceDetail>()
        for (item in deviceDetail){
            for (items in item){
                device.add(items)
            }
        }
        return device
    }
    fun getAllZones():ArrayList<Zone>{
        val zoneNameList = ArrayList<Zone>()
        zoneNameList.clear()
        for (item in zones){
            zoneNameList.add(item)
        }
        zoneNameList.add(0, Zone(nameEn = "All"))
        return zoneNameList
    }
    fun getFaultDevicesNum(): ArrayList<DeviceDetail> {
        val allDevices = getAllDevices()
        val getAllFaultDevices = ArrayList<DeviceDetail>()
        getAllFaultDevices.clear()
        for (item in allDevices){
            if (item.status == "Dis" || item.status == "UV Issue"){
                getAllFaultDevices.add(item)
            }
        }
        return getAllFaultDevices
    }
    fun getAllNormalDevicesNum() = getAllDevices().size - getFaultDevicesNum().size
    fun getAllOnDevicesNum(): ArrayList<DeviceDetail> {
        val allDevices = getAllDevices()
        val getAllOnDevices = ArrayList<DeviceDetail>()
        getAllOnDevices.clear()
        for (item in allDevices){
            if (item.status == "ON"){
                getAllOnDevices.add(item)
            }
        }
        return getAllOnDevices
    }
}
data class AvgAqi(
    @SerializedName("co2")
    val co2: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("pm")
    val pm: String,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("voc")
    val voc: String
){
    fun getCo2TextColor(){

    }
    enum class TextColor(){

    }
}
data class Zone(
    @SerializedName("aqi")
    val aqi: Aqi ?= null,
    @SerializedName("devices")
    val devices: Devices ?= null,
    @SerializedName("name_en")
    val nameEn: String ?= null,
    @SerializedName("zone_id")
    val zoneId: Int ?= null
)
data class Aqi(
    @SerializedName("co2")
    val co2: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("pm")
    val pm: String,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("voc")
    val voc: String
)
data class Devices(
    @SerializedName("device_details")
    val deviceDetails: List<DeviceDetail>,
    @SerializedName("machines_details")
    val machinesDetails: List<Any>
)
data class DeviceDetail(
    @SerializedName("dev_name")
    val devName: String,
    @SerializedName("device_type")
    val deviceType: String,
    @SerializedName("fan_speed")
    val fanSpeed: String,
    @SerializedName("hours")
    val hours: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_issue")
    val isIssue:String,
    @SerializedName("mac")
    val mac: String,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("uv")
    val uv: String
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTextWithBackground() = when(status){
        "ON" -> DeviceStatus.ON
        "OFF" -> DeviceStatus.OFF
        "Dis" -> DeviceStatus.DIS
        "UV Issue" -> DeviceStatus.UV_ISSUE
        "Med" -> DeviceStatus.ON
        else -> DeviceStatus.DEFAULT
    }
    enum class DeviceStatus(val statusTxt:String, val bgc: Int){
        @RequiresApi(Build.VERSION_CODES.O)
        ON("OK - ON",Color.argb(1f,0.2f,0.7f,0.4f)),
        @RequiresApi(Build.VERSION_CODES.O)
        OFF("PAUSED",Color.argb(1f,0.18f,0.26f,0.53f)),
        @RequiresApi(Build.VERSION_CODES.O)
        DIS("ERROR: COMM", Color.argb(1f,0.9f,0.1f,0.15f)),
        @RequiresApi(Build.VERSION_CODES.O)
        UV_ISSUE("WARN: UV",Color.argb(1f,0.9f,0.4f,0.25f)),
        @RequiresApi(Build.VERSION_CODES.O)
        DEFAULT("OK - ON",Color.argb(1f,0.2f,0.7f,0.4f))
    }
}

data class HistoryLastMonthData(
    @SerializedName("indoor")
    val indoor: HistoryIndoor,
    @SerializedName("lastMonth")
    val lastMonth: List<HistoryLastDate>,
    @SerializedName("monitor_service")
    val monitorService: Int,
    @SerializedName("outdoor")
    val outdoor: HistoryOutdoor
)
data class HistoryLastWeekData(
    @SerializedName("indoor")
    val indoor: HistoryIndoor,
    @SerializedName("lastWeek")
    val lastWeek: List<HistoryLastDate>,
    @SerializedName("monitor_service")
    val monitorService: Int,
    @SerializedName("outdoor")
    val outdoor: HistoryOutdoor
)
data class HistoryLast72HData(
    @SerializedName("indoor")
    val indoor: HistoryIndoor,
    @SerializedName("latest72h")
    val latest72h: List<HistoryLastDate>,
    @SerializedName("monitor_service")
    val monitorService: Int,
    @SerializedName("outdoor")
    val outdoor: HistoryOutdoor
)
data class HistoryIndoor(
    @SerializedName("display_param")
    val displayParam: Int,
    @SerializedName("indoor_co2")
    val indoorCo2: Int,
    @SerializedName("indoor_humidity")
    val indoorHumidity: Int,
    @SerializedName("indoor_pm")
    val indoorPm: Int,
    @SerializedName("indoor_temperature")
    val indoorTemperature: Int,
    @SerializedName("indoor_time")
    val indoorTime: String,
    @SerializedName("indoor_voc")
    val indoorVoc: Int,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("param_label")
    val paramLabel: String
)
data class HistoryLastDate(
    @SerializedName("avg_co2")
    val avgCo2: Int,
    @SerializedName("avg_humidity")
    val avgHumidity: Int,
    @SerializedName("avg_reading")
    val avgReading: Int,
    @SerializedName("avg_temperature")
    val avgTemperature: Int,
    @SerializedName("avg_tvoc")
    val avgTvoc: String,
    @SerializedName("date_reading")
    val dateReading: String,
    @SerializedName("reading_comp")
    val readingComp: Int
)
data class HistoryOutdoor(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("outdoor_display_param")
    val outdoorDisplayParam: Int,
    @SerializedName("outdoor_name_en")
    val outdoorNameEn: String,
    @SerializedName("outdoor_pm")
    val outdoorPm: Int,
    @SerializedName("outdoor_time")
    val outdoorTime: String
)