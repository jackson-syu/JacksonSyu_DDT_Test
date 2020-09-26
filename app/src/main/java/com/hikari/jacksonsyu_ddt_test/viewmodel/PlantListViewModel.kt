package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

/**
 * Created by hikari on 2020/9/26
 */
class PlantListViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "PlantListViewModel"
    }

    fun onItemClick() {
        Log.d(TAG, "onItemClick~")
    }


}