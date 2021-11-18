package com.cas.casdashboard.https

import okio.IOException

/**
 * @author Benjamin
 * @description:
 * @date :2021.9.17 14:40
 */
class ApiException(val error:ApiResultError): IOException(error.errorMsg)