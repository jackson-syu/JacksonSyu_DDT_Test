package com.hikari.jacksonsyu_ddt_test.api

/**
 * Created by hikari on 2020/9/24.
 */
class ApiConnection {
    companion object {
//        val TEST_URL: String = "http://www.json-generator.com/api/json/get/ceCCNXaSle?indent=2"
        val TEST_URL: String = "https://api.mocki.io/v1/2a6c9f89"


        fun getUrlDomain(url: String): String {
            return url.substring(0, url.lastIndexOf("/") + 1)
        }

        fun getUrlPath(url: String): String {
            return url.substring(url.lastIndexOf("/") + 1, url.length)
        }
    }
}