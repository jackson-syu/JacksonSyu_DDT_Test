package com.hikari.jacksonsyu_ddt_test.util

import android.os.AsyncTask
import okhttp3.ResponseBody
import java.io.File
import java.lang.Exception

/**
 * Created by hikari on 2020/9/27
 */
class ResponseBodyToFileTask(responseBody: ResponseBody, file: File, callBack: CallBack) : AsyncTask<Unit, Unit, Unit>() {

    private lateinit var responseBody: ResponseBody
    private lateinit var file: File
    private var callBack: CallBack? = null

    init {
        this.responseBody = responseBody
        this.file = file
        this.callBack = callBack
    }

    override fun doInBackground(vararg p0: Unit?) {
        try {
            IOHelper.writeResponseToFile(responseBody, file)
        }catch (e: Exception) {
            if(callBack != null) {
                callBack?.onError(e.message.toString())
            }
        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        if(callBack != null) {
            callBack?.onSuccess()
        }

    }

    interface CallBack {
        fun onSuccess()
        fun onError(error: String)
    }

}