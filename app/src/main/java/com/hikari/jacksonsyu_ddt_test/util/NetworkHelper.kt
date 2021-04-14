package com.hikari.jacksonsyu_ddt_test.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Created by hikari on 2020/9/28
 */
class NetworkHelper {
    companion object {
        private const val TAG = "NetworkHelper"

        fun checkNetwork(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            return isConnected
        }
    }
}