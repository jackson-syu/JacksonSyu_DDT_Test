package com.hikari.jacksonsyu_ddt_test.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by hikari on 2020/9/28.
 */
@Dao
interface PlantDataDao {
    @Query("SELECT * FROM " + PlantDataEntity.TABLE_NAME)
    fun getAll(): Observable<List<PlantDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entitys: List<PlantDataEntity>): Completable

    @Query("DELETE FROM " + PlantDataEntity.TABLE_NAME)
    fun deleteAll(): Completable
}