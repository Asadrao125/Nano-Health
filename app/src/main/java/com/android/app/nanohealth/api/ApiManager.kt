package com.android.app.nanohealth.api

import android.app.Activity
import android.util.Log
import com.android.app.nanohealth.utils.Const
import com.android.app.nanohealth.utils.Dialog_CustomProgress
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class ApiManager {
    val DEFAULT_TIMEOUT = 1000000000
    var activity: Activity
    var getOrPost: String
    var apiName: String
    var params: RequestParams? = null
    var apiCallback: ApiCallback
    var customProgressDialog: Dialog_CustomProgress
    var jsonObject: JSONObject? = null

    constructor(
        activity: Activity,
        getOrPost: String,
        apiName: String,
        params: RequestParams,
        apiCallback: ApiCallback
    ) {
        this.activity = activity
        this.getOrPost = getOrPost
        this.apiName = apiName
        this.params = params
        this.apiCallback = apiCallback
        customProgressDialog = Dialog_CustomProgress(activity)
        println("-- Req URL : " + BASE_URL + apiName)
        System.out.println("-- Params : " + params.toString())
    }

    constructor(
        activity: Activity,
        getOrPost: String,
        apiName: String,
        jsonObject: JSONObject,
        apiCallback: ApiCallback
    ) {
        this.activity = activity
        this.getOrPost = getOrPost
        this.apiName = apiName
        this.jsonObject = jsonObject
        this.apiCallback = apiCallback
        customProgressDialog = Dialog_CustomProgress(activity)
        println("-- Req URL : " + BASE_URL + apiName)
        println("-- Params : " + jsonObject.toString())
    }

    fun loadURL(loader: Int) {
        if (loader == 1) {
            customProgressDialog.show()
        }
        val client = AsyncHttpClient()
        client.setTimeout(DEFAULT_TIMEOUT)
        client.get(BASE_URL + apiName, params, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header?>?,
                responseBody: ByteArray?
            ) {
                try {
                    customProgressDialog.hide()
                    val content = String(responseBody!!)
                    apiCallback.onApiResponce(statusCode, 1, apiName, content)
                    Log.d("onSuccess", "Success: $content")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header?>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                try {
                    customProgressDialog.hide()
                    val content = String(responseBody!!)
                    Log.d("onFailure", "Failure: $content")
                    apiCallback.onApiResponce(statusCode, 0, apiName, content)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        )
    }

    fun jsonPost() {
        customProgressDialog.show()
        val client = AsyncHttpClient()
        client.setTimeout(DEFAULT_TIMEOUT)
        try {
            val entity = StringEntity(jsonObject.toString())
            client.post(
                activity,
                BASE_URL + apiName,
                entity,
                "application/json",
                object : AsyncHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<Header>,
                        responseBody: ByteArray
                    ) {
                        customProgressDialog.hide()
                        val content = String(responseBody)
                        apiCallback.onApiResponce(statusCode, 1, apiName, content)
                        Log.d("onSuccess", "Success: $content")
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<Header>,
                        responseBody: ByteArray,
                        error: Throwable
                    ) {
                        customProgressDialog.hide()
                        val content = String(responseBody)
                        apiCallback.onApiResponce(statusCode, 1, apiName, content)
                        Log.d("onFailure", "Failure: $content")
                    }
                })
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    companion object {
        var shouldShowPD = true
        var BASE_URL: String = Const.BASE_URL
    }
}