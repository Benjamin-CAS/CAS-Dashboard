package com.cas.casdashboard.util

import com.cas.casdashboard.R

/**
 * @author Benjamin
 * @description:
 * @date :2022.1.10 13:22
 */
fun getColorResFromCO2(co2: Double): Int {
    return if (co2 == 0.0) {
        R.color.aqi_beyond //beyond value
    } else if (co2 > 0 && co2 < 700) {
        R.color.aqi_good
    } else if (co2 >= 700 && co2 < 1000) {
        R.color.aqi_moderate
    } else {
        R.color.aqi_hazardous
    }
}

fun getColorResFromVoc(voc: Double): Int {
    return if (voc == 0.0) {
        R.color.aqi_good
    } else if (voc > 0 && voc < 0.55) {
        R.color.aqi_good
    } else if (voc >= 0.55 && voc < 0.65) {
        R.color.aqi_moderate
    } else {
        R.color.aqi_hazardous
    }

}

fun getColorResFromTmp(temp: Double): Int {
    return if (temp < 17) {
        R.color.aqi_hazardous
    } else if (temp >= 17 && temp < 19) {
        R.color.aqi_moderate
    } else if (temp >= 25 && temp < 27) {
        R.color.aqi_moderate
    } else if (temp >= 27) {
        R.color.aqi_hazardous
    } else {
        R.color.aqi_good
    }

}

fun getColorResFromHumid(humid: Double): Int {
    return if (humid < 35) {
        R.color.aqi_hazardous
    } else if (humid >= 35 && humid < 45) {
        R.color.aqi_moderate
    } else if (humid >= 65 && humid < 75) {
        R.color.aqi_moderate
    } else if (humid >= 75) {
        R.color.aqi_hazardous
    } else {
        R.color.aqi_good
    }
}