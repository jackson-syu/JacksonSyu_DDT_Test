package com.hikari.jacksonsyu_ddt_test.api

import com.hikari.jacksonsyu_ddt_test.base.BaseModelService
import com.hikari.jacksonsyu_ddt_test.util.ResponseBodyToFileTask
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File

/**
 * Created by hikari on 2020/9/25.
 */
class DownFileService : BaseModelService{

    companion object {

        private var disposable: Disposable? = null

        fun downloadCvsFile(url: String, file: File, callback: CallBack?) {

            val baseUrl: String = ApiConnection.getUrlDomain(url)

            RetrofitManager.Load(OkHttpManager.ClinetBuild().builder())
                .initRetrofit(baseUrl, RxJava2CallAdapterFactory.create())
                .downloadCvsFileObe(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.newThread())
                .subscribe({

                    ResponseBodyToFileTask(it.body()!!, file, object : ResponseBodyToFileTask.CallBack {
                        override fun onSuccess() {
                            if(callback != null) {
                                callback.onSuccess(it.message(), it.code())
                            }
                        }

                        override fun onError(error: String) {
                            if(callback != null) {
                                callback.onError(error)
                            }
                        }

                    }).execute()

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