package com.hikari.jacksonsyu_ddt_test.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hikari.jacksonsyu_ddt_test.model.TestModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by hikari on 2020/9/24.
 */
class RetrofitManager {

    companion object {
        private const val TAG = "RetrofitManager"
    }

    class Load(client: OkHttpClient) {

        lateinit var retrofit: Retrofit

        private lateinit var client: OkHttpClient

        init {
            this.client = client
        }

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

        fun getTestObe(path: String): Observable<Response<TestModel>> {
            var service: ApiService = retrofit.create(ApiService::class.java)
            return service.getTestData(path)
        }

        interface ApiService {
            @GET("{path}")
            fun getTestData(
                @Path("path") path: String
            ): Observable<Response<TestModel>>
        }

    }
}