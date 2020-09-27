package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hikari.jacksonsyu_ddt_test.data.MuseumListRepository
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.view.PlantListFragment
import com.hikari.jacksonsyu_ddt_test.view.ZooMuseumActicvity

/**
 * Created by hikari on 2020/9/26
 */
class MuseumListViewModel(application: Application) : AndroidViewModel(application){

    private var museumLiveData: MutableLiveData<List<MuseumDataModel>>? = MutableLiveData()
    private var repo: MuseumListRepository? = null

    companion object {
        private const val TAG = "MuseumListFgViewModel"
    }

    init {
        repo = MuseumListRepository.getInstance(application)
        museumLiveData = repo?.getMuseumListLiveData()
    }

    fun getMuseumLiveData(): MutableLiveData<List<MuseumDataModel>>? {
        return museumLiveData
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
    fun onItemClick(activity: Activity, museumDataModel: MuseumDataModel) {
        Log.d(TAG, "onItemClick~")
        if(activity is ZooMuseumActicvity) {
            var fragment: PlantListFragment = PlantListFragment.newInstance()
            fragment.setMuseumData(museumDataModel)
            activity.toFragment(fragment)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared~")
    }
}