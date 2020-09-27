package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.hikari.jacksonsyu_ddt_test.view.ZooMuseumActicvity

/**
 * Created by hikari on 2020/9/27
 */
class PlantDataViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "PlantDataViewModel"
    }

    fun onBack(activity: Activity) {
        Log.d(TAG, "onBack~")

        if(activity is ZooMuseumActicvity) {
            activity.onBack()
        }
    }
}