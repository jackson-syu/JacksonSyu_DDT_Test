package com.hikari.jacksonsyu_ddt_test.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hikari.jacksonsyu_ddt_test.api.ApiConnection
import com.hikari.jacksonsyu_ddt_test.api.DownFileService
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.room.MuseumDataDao
import com.hikari.jacksonsyu_ddt_test.room.MuseumDataEntity
import com.hikari.jacksonsyu_ddt_test.room.MuseumDatabase
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
import com.hikari.jacksonsyu_ddt_test.util.ToastHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference
import java.lang.reflect.Type

/**
 * Created by hikari on 2020/9/27
 */
class MuseumListRepository private constructor(context: Context){

    private val museumListLiveData: MutableLiveData<List<MuseumDataModel>>? = MutableLiveData()
    private var context: Context? = null

    private var dao: MuseumDataDao? = null
    private var dbDisposable: Disposable? = null

    companion object {

        private const val TAG = "MuseumListRepository"

        const val TYPE_FIRST = 0
        const val TYPE_NO_FIRST = 1

        @Volatile
        private var INSTANCE: MuseumListRepository? = null

        fun getInstance(context: Context): MuseumListRepository? {
            INSTANCE ?: synchronized(this) {
                if(INSTANCE == null) {
                    INSTANCE = MuseumListRepository(context)
                }
            }
            return INSTANCE
        }

    }

    init {
        var weakContext: WeakReference<Context> = WeakReference(context)
        this.context = weakContext.get()

        dao = MuseumDatabase.getInstance(weakContext.get()!!)?.getMuseumDataDao()
    }

    fun getMuseumListLiveData(): MutableLiveData<List<MuseumDataModel>>? {

        loadMuseumListFileData(context!!, TYPE_FIRST)

        return museumListLiveData
    }

    fun loadMuseumListFileData(context: Context, enterType: Int) {

        val url: String = ApiConnection.ZOO_MUSEUM_LIST_URL

        val fileName: String = "museum_data.csv"
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

                Log.d(TAG, "loadMuseumListData onSuccess code: " + code)

                if (code == 200) {

                    val csvMapList: MutableList<Map<String, String>>? = IOHelper.csvToMapList(
                        file,
                        "big5"
                    )
                    val gson: Gson = GsonBuilder().disableHtmlEscaping().create()
                    val json: String = gson.toJson(csvMapList)

                    val type: Type = object : TypeToken<ArrayList<MuseumDataModel>>() {}.type
                    val museumList: List<MuseumDataModel>? = gson.fromJson<List<MuseumDataModel>>(
                        json,
                        type
                    )
                    if (enterType == TYPE_FIRST) {
                        museumListLiveData?.value = museumList
                    } else {
                        museumListLiveData?.postValue(museumList)
                    }

                    //新增DB
                    insertAllMuseumDataToDB(MuseumDataModel.flatEntityListFromModelList(museumList))

                }
            }

            override fun onError(error: String) {
                Log.d(TAG, "loadMuseumListData onError error: " + error)
                ToastHelper.showToast(context, "載入本地資料...")

                getMuseumDataFromDB(museumListLiveData, TYPE_NO_FIRST)
            }

        })
    }

    //-----room-----

    fun getMuseumDataFromDB(museumListLiveData: MutableLiveData<List<MuseumDataModel>>?, enterType: Int) {
        if(dao == null) {
            return
        }

        dao?.getAll()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.observeOn(Schedulers.newThread())
            ?.subscribe({

                Log.d(TAG, "getMuseumDataFromDB onSuccess")
//                Log.d(TAG, "getMuseumDataFromDB data: " + it.toString())
                if(museumListLiveData != null) {
                    if (enterType == TYPE_FIRST) {
                        museumListLiveData.value = MuseumDataModel.flatModelListFromEntityList(it)
                    } else {
                        museumListLiveData.postValue(MuseumDataModel.flatModelListFromEntityList(it))
                    }
                }

            }, {

                Log.d(TAG, "getMuseumDataFromDB onError")
                ToastHelper.showToast(context, "載入失敗")
            }, {

            }, {

            }).let {
                dbDisposable = it
            }

    }

    fun insertAllMuseumDataToDB(museumDataEntitys: List<MuseumDataEntity>?) {
        if(museumDataEntitys == null || museumDataEntitys.size == 0 || dao == null) {
            return
        }

        dao?.insertAll(museumDataEntitys)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.observeOn(Schedulers.newThread())
            ?.subscribe({
                Log.d(TAG, "insertAllMuseumDataToDB onSuccess")
                ToastHelper.showToast(context, "新增資料庫成功")
            }, {
                Log.d(TAG, "insertAllMuseumDataToDB onError: " + it.message)
                ToastHelper.showToast(context, "新增資料庫失敗")
            }).let {
                dbDisposable = it
            }

    }

    fun deleteAllMuseumDataDB() {
        if(dao == null) {
            return
        }

        dao?.deleteAll()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.observeOn(Schedulers.newThread())
            ?.subscribe({
                Log.d(TAG, "deleteAllMuseumDataDB onSuccess")
//                ToastHelper.showToast(context, "刪除資料庫成功")
            }, {
                Log.d(TAG, "deleteAllMuseumDataDB onError: " + it.message)
//                ToastHelper.showToast(context, "刪除資料庫失敗")
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