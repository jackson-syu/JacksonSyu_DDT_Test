package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.data.PlantListRepository
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.model.PlantDataModel
import com.hikari.jacksonsyu_ddt_test.view.InAppBrowserFragment
import com.hikari.jacksonsyu_ddt_test.view.MuseumListFragment
import com.hikari.jacksonsyu_ddt_test.view.PlantDataFragment
import com.hikari.jacksonsyu_ddt_test.view.ZooMuseumActicvity
import kotlinx.android.synthetic.main.activity_zoo_museum_acticvity.view.*

/**
 * Created by hikari on 2020/9/26
 */
class PlantListViewModel(application: Application) : AndroidViewModel(application) {

    private var plantLiveData: MutableLiveData<List<PlantDataModel>>? = MutableLiveData()
    private var repo: PlantListRepository? = null

    companion object {
        private const val TAG = "PlantListViewModel"
    }

    init {
        repo = PlantListRepository.getInstance(application)
        plantLiveData = repo?.getPlantListLiveData()
    }

    fun getPlantLiveData(): MutableLiveData<List<PlantDataModel>>? {
        return plantLiveData
    }

    fun onBack(activity: Activity) {
        if(activity is ZooMuseumActicvity) {
            var fragment: MuseumListFragment = MuseumListFragment.newInstance()
            activity.fragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, fragment, fragment::javaClass.name)
                ?.commitAllowingStateLoss()
        }
    }

    //-----item-----

    fun onItemClick(activity: Activity, plantDataModel: PlantDataModel, museumDataModel: MuseumDataModel) {
        Log.d(TAG, "onItemClick~")
        if(activity is ZooMuseumActicvity) {
            var fragment: PlantDataFragment = PlantDataFragment.newInstance()
            fragment.setMuseumData(museumDataModel)
            fragment.setPlantData(plantDataModel)
            activity.toFragment(fragment)
        }
    }

    //-----header-----
    fun onBrowserClick(activity: Activity, title: String?, url: String?) {
        if(url != null && !url.equals("")) {
            Log.d(TAG, "onBrowserClick url: " + url)
            if(activity is ZooMuseumActicvity) {

                var inAppBrowserFragment: InAppBrowserFragment = InAppBrowserFragment.newInstance(title, url)
                inAppBrowserFragment.setCaller(object : InAppBrowserFragment.ContentPageCaller {
                    override fun onClose() {
                        activity.floatDownFragment(inAppBrowserFragment)
                    }

                    override fun onContentPageAnimEnd() {
                        activity.mBinding.webFragment.visibility = View.GONE
                    }

                })
                activity.floatUpFragment(inAppBrowserFragment)
            }
        }
    }

}