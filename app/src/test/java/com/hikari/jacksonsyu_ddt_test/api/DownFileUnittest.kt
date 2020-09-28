package com.hikari.jacksonsyu_ddt_test.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hikari.jacksonsyu_ddt_test.model.PlantDataModel
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
import org.junit.Before
import org.junit.Test
import java.io.*
import java.lang.Exception
import java.lang.reflect.Type
import java.util.ArrayList
import java.util.concurrent.CountDownLatch

/**
 * Created by hikari on 2020/9/25.
 */
class DownFileUnittest {

    lateinit var latch: CountDownLatch
    var json: String = ""

    @Before
    fun init() {
        latch = CountDownLatch(1)
    }

    @Test
    fun run() {

        //C:\Users\hikari\AppData\Local\Temp\test8159657733206182691.csv

//        val file: File = File.createTempFile("test", ".csv")
////        val outputPath: String = "C:\\Users\\Asus\\AppData\\Local\\Temp\\test756965683343442711.csv"
//        val outputPath: String = file.absolutePath
//        println(outputPath)
//        val testFile: File = File(outputPath)

        //別點位置
//        val testFile: File = File("D:\\HikariWorkspace\\DDTWorkspace\\app\\src\\test\\res\\", "test.csv")
        val testFile: File = File("D:\\HikariWorkspace\\DDTWorkspace\\app\\src\\test\\res\\", "test_plant.csv")

        //動物園館區資料
//        val testFile: File = File("E:\\android\\DDTWorkspace\\JacksonSyu_DDT_Test\\app\\src\\test\\res\\", "test.csv")
//        val url: String = ApiConnection.ZOO_MUSEUM_LIST_URL

        //植物資料
//        val testFile: File = File("E:\\android\\DDTWorkspace\\JacksonSyu_DDT_Test\\app\\src\\test\\res\\", "test_plant.csv")
        val url: String = ApiConnection.PLANT_DATA_URL

        DownFileService.downloadCvsFile(url, testFile, object : DownFileService.CallBack {
            override fun onSuccess(jsonString: String, code: Int) {
                json = jsonString + ", " + code
                latch.countDown()
            }

            override fun onError(error: String) {
                json = "error: " + error
                latch.countDown()
            }

        })

        try {
            latch.await()

            println(json)

            //動物園館區資料
//            var csvMapList: MutableList<Map<String, String>>? = IOHelper.csvToMapList(testFile, "big5")
            //植物資料
            var csvMapList: MutableList<Map<String, String>>? = IOHelper.csvToMapList(testFile, "UTF-8")

            var gson: Gson = GsonBuilder().disableHtmlEscaping().create()
            json = gson.toJson(csvMapList)
            println(json)

            println("-----------------------------------------------")

            var type: Type = object : TypeToken<ArrayList<PlantDataModel>>(){}.type
            var plantList: List<PlantDataModel> = gson.fromJson<List<PlantDataModel>>(json, type)

            for(plant in plantList) {
                println(plant.toString())
            }

            println("----------------------------------------------")

            for (map in csvMapList!!) {
                println()
                for ((key, value) in map) {
                    println("key: " + key + ", value: " + value)
                }
            }



        }catch (e: Exception) {
            println("run error: " + e.message)
        }

    }
}