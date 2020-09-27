package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hikari.jacksonsyu_ddt_test.data.MuseumListRepository
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel

/**
 * Created by hikari on 2020/9/26
 */
class MuseumListViewModel(application: Application) : AndroidViewModel(application){

    private var museumliveData: MutableLiveData<List<MuseumDataModel>>? = MutableLiveData()
    private var repo: MuseumListRepository? = null

    companion object {
        private const val TAG = "MuseumListFgViewModel"
    }

    init {

        repo = MuseumListRepository.getInstance(application)
        museumliveData = repo?.getMuseumListLiveData()
    }

    fun getMuseumLiveData(): MutableLiveData<List<MuseumDataModel>>? {
        return museumliveData
    }

    fun onMenuClick(context: Context) {
        Log.d(TAG, "onMenuClick~")

        val dialog: AlertDialog = AlertDialog.Builder(context)
            .setMessage("臺北市立動物園DDT\n\nv1.0.0\n\nBy Jackson.Syu")
            .setOnCancelListener(DialogInterface.OnCancelListener {
                it.dismiss()
            }).create()

        dialog.show()
    }

    //-----item-----
    fun onItemClick() {
        Log.d(TAG, "onItemClick~")
    }
}