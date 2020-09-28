package com.hikari.jacksonsyu_ddt_test.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by hikari on 2020/9/28.
 */
@Database(entities = [PlantDataEntity::class], version = 1)
abstract class PlantDatabase : RoomDatabase() {

    companion object {
        private const val TAG = "PlantDatabase"

        private const val DB_NAME = "plant_data.db"

        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getInstance(context: Context): PlantDatabase? {
            INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = create(context)
                }
            }
            return INSTANCE
        }

        private fun create(context: Context): PlantDatabase? {
            return Room.databaseBuilder(
                context.applicationContext,
                PlantDatabase::class.java,
                DB_NAME
            ).build()
        }

        fun destoryInstance() {
            INSTANCE = null
        }
    }

    abstract fun getPlantDataDao(): PlantDataDao
}