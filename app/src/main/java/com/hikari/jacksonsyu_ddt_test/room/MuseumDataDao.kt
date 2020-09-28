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
interface MuseumDataDao {

    @Query("SELECT * FROM " + MuseumDataEntity.TABLE_NAME)
    fun getAll(): Observable<List<MuseumDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entitys: List<MuseumDataEntity>): Completable

    @Query("DELETE FROM " + MuseumDataEntity.TABLE_NAME)
    fun deleteAll(): Completable

}