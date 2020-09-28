package com.hikari.jacksonsyu_ddt_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by hikari on 2020/9/28.
 */
@Entity(tableName = MuseumDataEntity.TABLE_NAME)
class MuseumDataEntity {
    companion object {
        private const val TAG = "MuseumDataEntity"

        const val TABLE_NAME = "museum_model"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var E_no: String = ""
    var E_Category: String = ""
    var E_Name: String = ""
    var E_Pic_URL: String = ""
    var E_Info: String = ""
    var E_Memo: String = ""
    var E_Geo: String = ""
    var E_URL: String = ""

}