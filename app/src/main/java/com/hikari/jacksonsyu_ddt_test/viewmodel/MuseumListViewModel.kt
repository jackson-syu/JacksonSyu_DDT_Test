package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

/**
 * Created by hikari on 2020/9/26
 */
class MuseumListViewModel(application: Application) : AndroidViewModel(application){

    companion object {
        private const val TAG = "MuseumListFgViewModel"
    }

    fun onMenuClick() {
        Log.d(TAG, "onMenuClick~")
    }

    //-----item-----
    fun onItemClick() {
        Log.d(TAG, "onItemClick~")
    }
}