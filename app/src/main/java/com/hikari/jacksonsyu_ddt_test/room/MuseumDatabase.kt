package com.hikari.jacksonsyu_ddt_test.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by hikari on 2020/9/28.
 */
@Database(entities = [MuseumDataEntity::class], version = 1)
abstract class MuseumDatabase : RoomDatabase() {

    companion object {
        private const val TAG = "MuseumDatabase"

        private const val DB_NAME = "museum_data.db"

        @Volatile
        private var INSTANCE: MuseumDatabase? = null

        fun getInstance(context: Context): MuseumDatabase? {
            INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = create(context)
                }
            }
            return INSTANCE
        }

        private fun create(context: Context): MuseumDatabase? {
            return Room.databaseBuilder(
                context.applicationContext,
                MuseumDatabase::class.java,
                DB_NAME
            ).build()
        }

        fun destoryInstance() {
            INSTANCE = null
        }
    }

    abstract fun getMuseumDataDao(): MuseumDataDao
}