package com.cas.casdashboard.https.response.decode

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
)
data class AvgAqi(
    @SerializedName("co2")
    val co2: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pm")
    val pm: Int,
    @SerializedName("temperature")
    val temperature: Int,
    @SerializedName("voc")
    val voc: String
)
data class Zone(
    @SerializedName("aqi")
    val aqi: Aqi,
    @SerializedName("devices")
    val devices: Devices,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("zone_id")
    val zoneId: Int
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
    @SerializedName("mac")
    val mac: String,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("uv")
    val uv: String
)
