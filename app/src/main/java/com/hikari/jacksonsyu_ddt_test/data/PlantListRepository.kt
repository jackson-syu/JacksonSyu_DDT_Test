package com.hikari.jacksonsyu_ddt_test.data

import android.content.Context

/**
 * Created by hikari on 2020/9/27
 */
class PlantListRepository private constructor(context: Context){

    companion object {

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

    fun downloadPlantListCsvFile() {

    }
}