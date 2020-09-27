package com.hikari.jacksonsyu_ddt_test.util

import android.os.Environment
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import okhttp3.ResponseBody
import java.io.*
import java.lang.Exception
import java.nio.charset.Charset

/**
 * Created by hikari on 2020/9/25.
 */
class IOHelper {

    companion object {
        private const val TAG = "IOHelper"

        /**
         * 寫入檔案
         */
        fun writeResponseToFile(responseBody: ResponseBody, file: File): Boolean {

            try {

                var inputStream: InputStream? = null
                var outputStream: OutputStream? = null

                try {

                    var fileReader = ByteArray(4096)
                    var fileSize = responseBody.contentLength()
                    var fileSizeDownloaded: Long = 0

                    inputStream = responseBody.byteStream()

                    outputStream = FileOutputStream(file)


                    while (true) {
                        val read = inputStream!!.read(fileReader)
                        if(read == -1) {
                            break
                        }
                        outputStream.write(fileReader, 0, read)
                        fileSizeDownloaded += read.toLong()

                    }

                    outputStream.flush()
                    return true

                }catch (e: Exception) {
                    println("error stream: " + e.message)
                }finally {
                    if(inputStream != null) {
                        inputStream.close()
                    }
                    outputStream?.close()
                }

            }catch (e: Exception) {
                println("error: " + e.message)
            }

            return false
        }

        fun csvToMapList(file: File, charsetType: String): MutableList<Map<String, String>> {
            val keyList: MutableList<String>? = mutableListOf()
            val csvMapList: MutableList<Map<String, String>>? = mutableListOf()

            var count = 0
            csvReader{
                charset = charsetType
            }.open(file.path) {
                readAllAsSequence().forEach {row: List<String> ->

                    if(count == 0) {
                        for(i in 0..row.size - 1) {
                            keyList?.add(row.get(i))
                        }
                    }else{
                        val map = mutableMapOf<String, String>()
                        for(j in 0..row.size - 1) {
                            map.put(keyList!!.get(j), row.get(j))
                        }
                        csvMapList?.add(map)
                    }

//                    println("no_" + count + ": " + row.toString())
                    count++
                }
            }

            return csvMapList!!
        }

    }
}