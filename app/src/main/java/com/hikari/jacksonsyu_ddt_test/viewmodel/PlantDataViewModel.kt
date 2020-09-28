package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.view.PlantListFragment
import com.hikari.jacksonsyu_ddt_test.view.ZooMuseumActicvity
import kotlinx.android.synthetic.main.activity_zoo_museum_acticvity.view.*

/**
 * Created by hikari on 2020/9/27
 */
class PlantDataViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "PlantDataViewModel"
    }

    fun onBack(activity: Activity, museumDataModel: MuseumDataModel) {
        Log.d(TAG, "onBack~")

        if(activity is ZooMuseumActicvity) {
            var fragment: PlantListFragment = PlantListFragment.newInstance()
            fragment.setMuseumData(museumDataModel)
            activity.fragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, fragment, fragment::javaClass.name)
                ?.commitAllowingStateLoss()
        }
    }
}