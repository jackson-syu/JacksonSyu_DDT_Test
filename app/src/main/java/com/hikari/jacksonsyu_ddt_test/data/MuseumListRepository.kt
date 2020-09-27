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
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
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

    companion object {

        private const val TAG = "MuseumListRepository"

        const val TYPE_FIRST = 0
        const val TYPE_NO_FIRST = 1

        @Volatile private var INSTANCE: MuseumListRepository? = null

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

                }
            }

            override fun onError(error: String) {
                Log.d(TAG, "loadMuseumListData onError error: " + error)
                Toast.makeText(context, "載入失敗", Toast.LENGTH_SHORT).show()
            }

        })
    }

}