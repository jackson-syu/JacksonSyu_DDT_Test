package com.hikari.jacksonsyu_ddt_test.util

import android.os.Environment
import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
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
                    outputStream.write(byteArrayOf())

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

    }
}