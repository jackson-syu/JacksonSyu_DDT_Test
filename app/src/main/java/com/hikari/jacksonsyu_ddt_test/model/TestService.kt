package com.hikari.jacksonsyu_ddt_test.model

import com.google.gson.Gson
import com.hikari.jacksonsyu_ddt_test.api.ApiConnection
import com.hikari.jacksonsyu_ddt_test.api.OkHttpManager
import com.hikari.jacksonsyu_ddt_test.api.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.*

/**
 * Created by hikari on 2020/9/24.
 */
class TestService {

    companion object {

        fun loadTest(callBack: CallBack? = null) {

            var url = ApiConnection.TEST_URL
            var baseUrl = ApiConnection.getUrlDomain(url)
            var pathUrl = ApiConnection.getUrlPath(url)

            RetrofitManager.Load(OkHttpManager.ClinetBuild().builder())
                .initRetrofit(baseUrl, RxJava2CallAdapterFactory.create())
                .getTestObe(pathUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({
                    if(callBack != null) {
                        var gson = Gson()
                        callBack.onSuccess(gson.toJson(it.body()))
                    }
                }, {
                    if(callBack != null) {
                        callBack.onError(it.message.toString())
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