package com.hikari.jacksonsyu_ddt_test.util

import android.os.AsyncTask
import java.lang.Exception

/**
 * Created by hikari on 2020/9/28
 */
class FlatDataTask(callBack: CallBack) : AsyncTask<Unit, Unit, Unit>() {

    private var callBack: FlatDataTask.CallBack? = callBack


    override fun doInBackground(vararg p0: Unit?) {
        try {
            if(callBack != null) {
                callBack?.doInBack()
            }
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
        fun doInBack()
        fun onSuccess()
        fun onError(error: String)
    }

}