package com.hikari.jacksonsyu_ddt_test.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hikari.jacksonsyu_ddt_test.model.TestModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by hikari on 2020/9/24.
 */
class RetrofitManager {

    companion object {
        private const val TAG = "RetrofitManager"
    }

    class Load(client: OkHttpClient) {

        var retrofit: Retrofit? = null

        private lateinit var client: OkHttpClient

        init {
            this.client = client
        }

        /**
         * 初始化retrofit
         */
        fun initRetrofit(baseUrl: String, factory: CallAdapter.Factory): Load {

            var gson: Gson = GsonBuilder().setLenient().create()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(factory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            return this
        }

        //-----取得obe-----
        /**
         * 取得測試 obe
         */
        fun getTestObe(path: String): Observable<Response<TestModel>> {
            val service: ApiService = retrofit!!.create(ApiService::class.java)
            return service.getTestData(path)
        }

        /**
         * 下載 cvs 檔案 obe
         */
        fun downloadCvsFileObe(fileUrl: String) :Observable<Response<ResponseBody>> {
            val service: ApiService = retrofit!!.create(ApiService::class.java)
            return service.downloadCvsFile(fileUrl)
        }

        /**
         * retrofit api 設定
         */
        interface ApiService {

            //test
            @GET("{index}")
            fun getTestData(
                @Path("index") path: String
            ): Observable<Response<TestModel>>

            //load cvs
            @Streaming
            @GET
            fun downloadCvsFile(
                @Url fileUrl: String
            ): Observable<Response<ResponseBody>>
        }


    }
}