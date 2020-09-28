package com.hikari.jacksonsyu_ddt_test.room

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by hikari on 2020/9/28.
 */
@Entity(tableName = PlantDataEntity.TABLE_NAME)
class PlantDataEntity {

    companion object {
        private const val TAG = "PlantDataEntity"

        const val TABLE_NAME = "plant_model"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var F_Name_Ch: String = ""
    var F_Summary: String = ""
    var F_Keywords: String = ""
    var F_AlsoKnown: String = ""
    var F_Geo: String = ""
    var F_Location: String = ""
    var F_Name_En: String = ""
    var F_Name_Latin: String = ""
    var F_Family: String = ""
    var F_Genus: String = ""
    var F_Brief: String = ""
    var F_Feature: String = ""
    var F_FunctionApplication: String = ""
    var F_Code: String = ""
    var F_Pic01_ALT: String = ""
    var F_Pic01_URL: String = ""
    var F_Pic02_ALT: String = ""
    var F_Pic02_URL: String = ""
    var F_Pic03_ALT: String = ""
    var F_Pic03_URL: String = ""
    var F_Pic04_ALT: String = ""
    var F_Pic04_URL: String = ""
    var F_pdf01_ALT: String = ""
    var F_pdf01_URL: String = ""
    var F_pdf02_ALT: String = ""
    var F_pdf02_URL: String = ""
    var F_Voice01_ALT: String = ""
    var F_Voice01_URL: String = ""
    var F_Voice02_ALT: String = ""
    var F_Voice02_URL: String = ""
    var F_Voice03_ALT: String = ""
    var F_Voice03_URL: String = ""
    var F_Vedio_URL: String = ""
    var F_Update: String = ""
    var F_CID: String = ""
}