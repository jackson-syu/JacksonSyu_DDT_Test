package com.hikari.jacksonsyu_ddt_test.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hikari.jacksonsyu_ddt_test.api.ApiConnection
import com.hikari.jacksonsyu_ddt_test.model.DownFileService
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.model.PlantDataModel
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
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

            Toast.makeText(context, "載入中請稍後...", Toast.LENGTH_SHORT).show()
        }else {
            Log.d(TAG, "createNewFile ~")
            file.parentFile.mkdirs()
            try {
                file.createNewFile()
                downloadCvsFile(url, file, enterType)
                Toast.makeText(context, "載入中請稍後...", Toast.LENGTH_SHORT).show()

            } catch (e: IOException) {
                Log.d(TAG, "createNewFile error ~")
                e.printStackTrace()
                Toast.makeText(context, "檔案新增失敗", Toast.LENGTH_SHORT).show()
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
                    }catch (e: Exception) {
                        e.printStackTrace()
                        Log.d(TAG, "error: " + e.message)
                    }

                }
            }

            override fun onError(error: String) {
                Log.d(TAG, "loadPlantListFileData onError error: " + error)
                Toast.makeText(context, "載入失敗", Toast.LENGTH_SHORT).show()
            }

        })
    }
}