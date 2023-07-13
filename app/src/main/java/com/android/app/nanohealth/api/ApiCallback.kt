package com.android.app.nanohealth.api

interface ApiCallback {
    fun onApiResponce(
        httpStatusCode: Int,
        successOrFail: Int,
        apiName: String?,
        apiResponce: String?
    )
}