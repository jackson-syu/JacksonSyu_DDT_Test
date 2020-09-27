package com.hikari.jacksonsyu_ddt_test.api

/**
 * Created by hikari on 2020/9/24.
 */
class ApiConnection {
    companion object {

        //測試api
        val TEST_URL: String = "https://api.mocki.io/v1/2a6c9f89"

        //動物園館區api
        val ZOO_MUSEUM_LIST_URL = "https://data.taipei/api/getDatasetInfo/downloadResource?id=1ed45a8a-d26a-4a5f-b544-788a4071eea2&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a"
        //植物資料api
        val PLANT_DATA_URL = "https://data.taipei/api/getDatasetInfo/downloadResource?id=48c4d6a7-4b09-4d1f-9739-ee837d302bd1&rid=f18de02f-b6c9-47c0-8cda-50efad621c14"

        /**
         * 取得 baseUrl
         */
        fun getUrlDomain(url: String): String {
            return url.substring(0, url.lastIndexOf("/") + 1)
        }

        /**
         * 取得 url path
         */
        fun getUrlPath(url: String): String {
            return url.substring(url.lastIndexOf("/") + 1, url.length)
        }
    }
}