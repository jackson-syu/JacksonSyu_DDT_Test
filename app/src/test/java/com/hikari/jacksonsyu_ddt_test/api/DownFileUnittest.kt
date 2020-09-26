package com.hikari.jacksonsyu_ddt_test.api

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.hikari.jacksonsyu_ddt_test.model.DownFileService
import com.hikari.jacksonsyu_ddt_test.util.IOHelper
import org.junit.Before
import org.junit.Test
import java.io.*
import java.lang.Exception
import java.net.URL
import java.util.ArrayList
import java.util.concurrent.CountDownLatch
import java.util.regex.Pattern

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


//        val testFile: File = File("D:\\HikariWorkspace\\DDTWorkspace\\app\\src\\test\\res\\", "test.cvs")
        val testFile: File = File("E:\\android\\DDTWorkspace\\JacksonSyu_DDT_Test\\app\\src\\test\\res\\", "test.csv")

        DownFileService.downloadCvsFile(testFile, object : DownFileService.CallBack {
            override fun onSuccess(jsonString: String) {
                json = jsonString
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

            var csvMapList: MutableList<Map<String, String>>? = IOHelper.csvToMapList(testFile, "big5")

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