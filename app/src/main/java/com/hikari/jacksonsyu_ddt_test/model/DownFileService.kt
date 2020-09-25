package com.hikari.jacksonsyu_ddt_test.model

import android.os.Environment
import com.hikari.jacksonsyu_ddt_test.api.ApiConnection
import com.hikari.jacksonsyu_ddt_test.api.OkHttpManager
import com.hikari.jacksonsyu_ddt_test.api.RetrofitManager
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.net.URL

/**
 * Created by hikari on 2020/9/25.
 */
class DownFileService {
    companion object {
        fun downloadCvsFile(file: File, callback: CallBack) {

            val url: String = ApiConnection.ZOO_MUSEUM_LIST_URL
            val baseUrl: String = ApiConnection.getUrlDomain(url)

            RetrofitManager.Load(OkHttpManager.ClinetBuild().builder())
                .initRetrofit(baseUrl, RxJava2CallAdapterFactory.create())
                .downloadCvsFileObe(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({

                    IOHelper.writeResponseToFile(it.body()!!, file)

                    if(callback != null) {
                        callback.onSuccess(it.message())
                    }

                }, {

                    if(callback != null) {
                        callback.onError(it.message.toString())
                    }

                }, {

                }, {

                }).let {

                }
        }
    }

    interface CallBack {
        fun onSuccess(jsonString: String)
        fun onError(error: String)
    }
}