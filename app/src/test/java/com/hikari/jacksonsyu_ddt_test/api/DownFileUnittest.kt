package com.hikari.jacksonsyu_ddt_test.api

import android.os.Environment
import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.hikari.jacksonsyu_ddt_test.model.DownFileService
import org.junit.Before
import org.junit.Test
import java.io.File
import java.lang.Exception
import java.net.URL
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

//        val file: File = File.createTempFile("test", ".cvs")

        val file: File = File("D:\\HikariWorkspace\\DDTWorkspace\\app\\src\\test\\res\\", "test.cvs")

//        val outputPath: String = "C:\\Users\\Asus\\AppData\\Local\\Temp\\test756965683343442711.cvs"//file.absolutePath
//        println(outputPath)
//        val testFile: File = File(outputPath)

        DownFileService.downloadCvsFile(file, object : DownFileService.CallBack {
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



            csvReader().open(file.path) {
                readAllAsSequence().forEach {row: List<String> ->
                    println(row)
                }
            }

        }catch (e: Exception) {
            println("run error: " + e.message)
        }

    }
}