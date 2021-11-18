package com.cas.casdashboard.https.response.encode


import com.cas.casdashboard.https.util.BaseJson

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.14 18:17
 */
data class CompanyLocation(
    override val code:Int,
    override val payload:String,
    override val status: Boolean,
    override val msg: String,
    val data:String
):BaseJson()
data class Encode(
    override var code:Int,
    override var status:Boolean,
    override var payload: String,
    override var msg:String
): BaseJson()
