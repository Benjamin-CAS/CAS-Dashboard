package com.cas.casdashboard.https.request

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.14 18:16
 */
// LocGetIpad
data class LocationList(
    val c:String,
    val d:String
)
// Login
data class GetLogin(
    val l:String,
    val user:String,
    val password:String
)

data class LocDataGetIpad(
    val c:String,
    val l:String,
    val h:String = "0",
    val w:String = "0",
    val d:String = "0",
    val user:String,
    val password:String
)
data class RequestGetExtLocInfo(
    val c:String,
    val l:String
)
data class RequestGetInterfaceDetails(
    val d:String,
    val user:String,
    val password:String
)
