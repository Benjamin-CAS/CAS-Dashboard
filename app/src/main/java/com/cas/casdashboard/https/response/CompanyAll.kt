package com.cas.casdashboard.https.response



data class CompanyAll(
    val code: String,
    val data: List<CompanyAllData>,
    val msg: String,
    val status: Boolean
)

data class CompanyAllData(
    val active: Int,    //
    val company_id: Int,   // 公司ID
    val name_en: String,  // 公司名字
    val outdoor: Int,  // 室外PM2.5数据
    val secure: Int  // 等于零不需要用户密码
)