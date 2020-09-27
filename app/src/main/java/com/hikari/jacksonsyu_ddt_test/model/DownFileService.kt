package com.hikari.jacksonsyu_ddt_test.model

import android.os.Environment
import com.hikari.jacksonsyu_ddt_test.api.ApiConnection
import com.hikari.jacksonsyu_ddt_test.api.OkHttpManager
import com.hikari.jacksonsyu_ddt_test.api.RetrofitManager
import com.hikari.jacksonsyu_ddt_test.base.BaseModelService
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.net.URL

/**
 * Created by hikari on 2020/9/25.
 */
class DownFileService : BaseModelService{

    companion object {

        private var disposable: Disposable? = null

        fun downloadCvsFile(url: String, file: File, callback: CallBack) {

            val baseUrl: String = ApiConnection.getUrlDomain(url)

            RetrofitManager.Load(OkHttpManager.ClinetBuild().builder())
                .initRetrofit(baseUrl, RxJava2CallAdapterFactory.create())
                .downloadCvsFileObe(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.newThread())
                .subscribe({

                    IOHelper.writeResponseToFile(it.body()!!, file)
//                    IOHelper.writeResponseToCsvFile(it.body()!!, file)

                    if(callback != null) {
                        callback.onSuccess(it.message(), it.code())
                    }

                }, {

                    if(callback != null) {
                        callback.onError(it.message.toString())
                    }

                }, {

                }, {
                    disposable = it as Disposable
                }).let {

                }
        }
    }

    interface CallBack {
        fun onSuccess(jsonString: String, code: Int)
        fun onError(error: String)
    }

    override fun onDestory() {
        if(disposable != null) {
            disposable?.dispose()
        }
    }
}