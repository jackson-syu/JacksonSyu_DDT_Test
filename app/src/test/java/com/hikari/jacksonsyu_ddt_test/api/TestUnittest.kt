package com.hikari.jacksonsyu_ddt_test.api

import com.hikari.jacksonsyu_ddt_test.model.TestService
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.util.concurrent.CountDownLatch

/**
 * Created by hikari on 2020/9/24.
 */
class TestUnittest {

    lateinit var latch: CountDownLatch
    var json: String = ""
    @Before
    fun init() {
        latch = CountDownLatch(1)
    }

    @Test
    fun run() {
        TestService.loadTest(object : TestService.CallBack{
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
        }catch (e: Exception) {
            println(e.message)
        }

    }

}