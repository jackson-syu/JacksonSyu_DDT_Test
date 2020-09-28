package com.hikari.jacksonsyu_ddt_test.repo

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hikari.jacksonsyu_ddt_test.api.ApiConnection
import com.hikari.jacksonsyu_ddt_test.api.DownFileService
import com.hikari.jacksonsyu_ddt_test.model.PlantDataModel
import com.hikari.jacksonsyu_ddt_test.room.PlantDataDao
import com.hikari.jacksonsyu_ddt_test.room.PlantDataEntity
import com.hikari.jacksonsyu_ddt_test.room.PlantDatabase
import com.hikari.jacksonsyu_ddt_test.util.FlatDataTask
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
import com.hikari.jacksonsyu_ddt_test.util.ToastHelper
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.ref.WeakReference
import java.lang.reflect.Type

/**
 * Created by hikari on 2020/9/27
 */
class PlantListRepository private constructor(context: Context){

    private var plantListLiveData: MutableLiveData<List<PlantDataModel>>? = MutableLiveData()
    private var context: Context? = null

    private var dao: PlantDataDao? = null
    private var dbDisposable: Disposable? = null

    companion object {

        private const val TAG = "PlantListRepository"

        const val TYPE_FIRST = 0
        const val TYPE_NO_FIRST = 1

        @Volatile
        private var INSTANCE: PlantListRepository? = null

        fun getInstance(context: Context): PlantListRepository? {
            INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = PlantListRepository(context)
                }
            }
            return INSTANCE
        }
    }

    init {
        var weakContext: WeakReference<Context> = WeakReference(context)
        this.context = weakContext.get()

        dao = PlantDatabase.getInstance(weakContext.get()!!)?.getPlantDataDao()
        Log.d(TAG, "dao: " + dao)
    }

    fun getPlantListLiveData(): MutableLiveData<List<PlantDataModel>>? {

        loadPlantListFileData(context!!, TYPE_FIRST)

        return plantListLiveData
    }

    fun loadPlantListFileData(context: Context, enterType: Int) {
        val url: String = ApiConnection.PLANT_DATA_URL

        val fileName: String = "plant_data.csv"
        val fileFolder: File = File(context.filesDir, "zooDataFolder")
        val file: File = File(fileFolder, fileName)

        if(file.exists()) {
            Log.d(TAG, "file exists ~")
            downloadCvsFile(url, file, enterType)

            ToastHelper.showToast(context, "載入中請稍後...")
        }else {
            Log.d(TAG, "createNewFile ~")
            file.parentFile?.mkdirs()
            try {
                file.createNewFile()
                downloadCvsFile(url, file, enterType)
                ToastHelper.showToast(context, "載入中請稍後...")

            } catch (e: IOException) {
                Log.d(TAG, "createNewFile error ~")
                e.printStackTrace()
                ToastHelper.showToast(context, "檔案新增失敗")
            }
        }

    }

    private fun downloadCvsFile(url: String, file: File, enterType: Int) {
        DownFileService.downloadCvsFile(url, file, object : DownFileService.CallBack {
            override fun onSuccess(jsonString: String, code: Int) {

                Log.d(TAG, "loadPlantListFileData onSuccess code: " + code)

                if (code == 200) {

                    try {
                        val csvMapList: MutableList<Map<String, String>>? = IOHelper.csvToMapList(file, "UTF-8")
                        val gson: Gson = GsonBuilder().disableHtmlEscaping().create()
                        val json: String = gson.toJson(csvMapList)

                        val type: Type = object : TypeToken<ArrayList<PlantDataModel>>() {}.type
                        val plantList: List<PlantDataModel>? = gson.fromJson<List<PlantDataModel>>(json, type)
                        if (enterType == TYPE_FIRST) {
                            plantListLiveData?.value = plantList
                        } else {
                            plantListLiveData?.postValue(plantList)
                        }

                        //新增DB
                        insertAllPlantDataToDB(PlantDataModel.flatEntityListFromModelList(plantList))

                    }catch (e: Exception) {
                        e.printStackTrace()
                        Log.d(TAG, "error: " + e.message)
                    }

                }
            }

            override fun onError(error: String) {
                Log.d(TAG, "loadPlantListFileData onError error: " + error)
                ToastHelper.showToast(context, "載入本地資料...")

                getPlantDataFromDB(plantListLiveData, enterType)
            }

        })
    }

    //-----room-----

    fun getPlantDataFromDB(plantListLiveData: MutableLiveData<List<PlantDataModel>>?, enterType: Int) {
        if(dao == null) {
            return
        }

        dao?.getAll()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.observeOn(Schedulers.newThread())
            ?.subscribe({
                Log.d(TAG, "getPlantDataFromDB onSuccess")
                if (enterType == TYPE_FIRST) {
                    plantListLiveData?.value = PlantDataModel.flatModelListFromEntityList(it)
                } else {
                    plantListLiveData?.postValue(PlantDataModel.flatModelListFromEntityList(it))
                }
            }, {
                Log.d(TAG, "getPlantDataFromDB onError")
                ToastHelper.showToast(context, "載入失敗")
            }, {

            }, {

            }).let {
                dbDisposable = it
            }
    }

    fun insertAllPlantDataToDB(plantDataEntitys: List<PlantDataEntity>?) {

        if(plantDataEntitys == null || plantDataEntitys.size == 0 || dao == null) {
            Log.d(TAG, "insertAllPlantDataToDB plantDataEntitys is null")
            return
        }

        dao?.deleteAll()
            ?.andThen(Completable.fromAction({
                Log.d(TAG, "insertAllPlantDataToDB deleteAll finish")
            }))
            ?.andThen(dao?.insertAll(plantDataEntitys))
            ?.andThen(Completable.fromAction({
                Log.d(TAG, "insertAllPlantDataToDB insertAll finish")
            }))
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.single())
            ?.subscribe({
                Log.d(TAG, "insertAllPlantDataToDB onSuccess")
                ToastHelper.showToast(context, "載入並新增資料庫成功")
            }, {
                Log.d(TAG, "insertAllPlantDataToDB onError: " + it.message)
                ToastHelper.showToast(context, "新增資料庫失敗")
            }).let {
                dbDisposable = it
            }

    }

    fun onDestory() {
        if(dbDisposable != null) {
            dbDisposable?.dispose()
        }
    }
}