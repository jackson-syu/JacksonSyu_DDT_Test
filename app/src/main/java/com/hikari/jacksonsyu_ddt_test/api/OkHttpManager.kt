package com.hikari.jacksonsyu_ddt_test.api

import android.content.Context
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by hikari on 2020/9/24.
 */
class OkHttpManager {

    companion object {
        private const val TAG = "OkHttpHelper"
    }

    class ClinetBuild() {


        private var mContext: Context? = null
        private var timeout: Long = 7

        constructor(mContext: Context) : this() {
            this.mContext = mContext
        }

        fun setTimeout(timeout: Long) {
            this.timeout = timeout
        }

        /**
         * clinet建置
         */
        fun builder(): OkHttpClient {
            var client: OkHttpClient
            var builder: OkHttpClient.Builder

            builder = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)

            client = builder.build()
            return client
        }

    }

}