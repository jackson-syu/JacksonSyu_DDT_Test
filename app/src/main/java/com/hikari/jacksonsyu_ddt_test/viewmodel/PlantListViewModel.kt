package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.hikari.jacksonsyu_ddt_test.view.ZooMuseumActicvity

/**
 * Created by hikari on 2020/9/26
 */
class PlantListViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "PlantListViewModel"
    }

    fun onBack(activity: Activity) {
        if(activity is ZooMuseumActicvity) {
            activity.onBack()
        }
    }

    //-----item-----

    fun onItemClick() {
        Log.d(TAG, "onItemClick~")
    }

    //-----header-----
    fun onBrowserClick() {

    }

}