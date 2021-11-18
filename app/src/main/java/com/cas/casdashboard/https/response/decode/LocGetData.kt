package com.cas.casdashboard.https.response.decode


import com.google.gson.annotations.SerializedName

data class LocGetData(
    @SerializedName("energy")
    val energy: Energy,
    @SerializedName("ExpEquip")
    val expEquip: Int,
    @SerializedName("ExpFilter")
    val expFilter: Int,
    @SerializedName("indoor")
    val indoor: Indoor,
    @SerializedName("monitor_service")
    val monitorService: Int,
    @SerializedName("outdoor")
    val outdoor: Outdoor
)
data class Energy(
    @SerializedName("current_max")
    val currentMax: Int,
    @SerializedName("current_used")
    val currentUsed: Int,
    @SerializedName("max")
    val max: Int,
    @SerializedName("month")
    val month: Int
)
data class Indoor(
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
    val indoorVoc: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("param_label")
    val paramLabel: String
)
data class Outdoor(
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