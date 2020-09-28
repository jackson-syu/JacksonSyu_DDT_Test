package com.hikari.jacksonsyu_ddt_test.model

import com.hikari.jacksonsyu_ddt_test.room.MuseumDataEntity
import com.hikari.jacksonsyu_ddt_test.room.PlantDataEntity

data class PlantDataModel(
    var F_AlsoKnown: String? = "",
    var F_Brief: String? = "",
    var F_CID: String? = "",
    var F_Code: String? = "",
    var F_Family: String? = "",
    var F_Feature: String? = "",
    var F_FunctionApplication: String? = "",
    var F_Genus: String? = "",
    var F_Geo: String? = "",
    var F_Keywords: String? = "",
    var F_Location: String? = "",
    var F_Name_Ch: String? = "",
    var F_Name_En: String? = "",
    var F_Name_Latin: String? = "",
    var F_Pic01_ALT: String? = "",
    var F_Pic01_URL: String? = "",
    var F_Pic02_ALT: String? = "",
    var F_Pic02_URL: String? = "",
    var F_Pic03_ALT: String? = "",
    var F_Pic03_URL: String? = "",
    var F_Pic04_ALT: String? = "",
    var F_Pic04_URL: String? = "",
    var F_Summary: String? = "",
    var F_Update: String? = "",
    var F_Vedio_URL: String? = "",
    var F_Voice01_ALT: String? = "",
    var F_Voice01_URL: String? = "",
    var F_Voice02_ALT: String? = "",
    var F_Voice02_URL: String? = "",
    var F_Voice03_ALT: String? = "",
    var F_Voice03_URL: String? = "",
    var F_pdf01_ALT: String? = "",
    var F_pdf01_URL: String? = "",
    var F_pdf02_ALT: String? = "",
    var F_pdf02_URL: String? = ""
) {
    companion object {
        private const val TAG = "PlantDataModel"

        /**
         * model 轉換 entity
         */
        fun flatEntityFromModel(model: PlantDataModel?): PlantDataEntity? {
            if(model != null) {
                val entity: PlantDataEntity = PlantDataEntity()

                entity.F_AlsoKnown = model.F_AlsoKnown!!
                entity.F_Brief = model.F_Brief!!
                entity.F_CID = model.F_CID!!
                entity.F_Code = model.F_Code!!
                entity.F_Family = model.F_Family!!
                entity.F_Feature = model.F_Feature!!
                entity.F_FunctionApplication = model.F_FunctionApplication!!
                entity.F_Genus = model.F_Genus!!
                entity.F_Geo = model.F_Geo!!
                entity.F_Keywords = model.F_Keywords!!
                entity.F_Location = model.F_Location!!
                entity.F_Name_Ch = model.F_Name_Ch!!
                entity.F_Name_En = model.F_Name_En!!
                entity.F_Name_Latin = model.F_Name_Latin!!
                entity.F_Pic01_ALT = model.F_Pic01_ALT!!
                entity.F_Pic01_URL = model.F_Pic01_URL!!
                entity.F_Pic02_ALT = model.F_Pic02_ALT!!
                entity.F_Pic02_URL = model.F_Pic02_URL!!
                entity.F_Pic03_ALT = model.F_Pic03_ALT!!
                entity.F_Pic03_URL = model.F_Pic03_URL!!
                entity.F_Pic04_ALT = model.F_Pic04_ALT!!
                entity.F_Pic04_URL = model.F_Pic04_URL!!
                entity.F_Summary = model.F_Summary!!
                entity.F_Update = model.F_Update!!
                entity.F_Vedio_URL = model.F_Vedio_URL!!
                entity.F_Voice01_ALT = model.F_Voice01_ALT!!
                entity.F_Voice01_URL = model.F_Voice01_URL!!
                entity.F_Voice02_ALT = model.F_Voice02_ALT!!
                entity.F_Voice02_URL = model.F_Voice02_URL!!
                entity.F_Voice03_ALT = model.F_Voice03_ALT!!
                entity.F_Voice03_URL = model.F_Voice03_URL!!
                entity.F_pdf01_ALT = model.F_pdf01_ALT!!
                entity.F_pdf01_URL = model.F_pdf01_URL!!
                entity.F_pdf02_ALT = model.F_pdf02_ALT!!
                entity.F_pdf02_URL = model.F_pdf02_URL!!

                return entity

            }
            return null
        }

        /**
         * modelList 轉換 entityList
         */
        fun flatEntityListFromModelList(modelList: List<PlantDataModel>?): List<PlantDataEntity>? {
            if(modelList != null && modelList.size != 0) {
                val entityList: MutableList<PlantDataEntity> = mutableListOf()

                for (i in 0..modelList.size - 1) {
                    val entity: PlantDataEntity = flatEntityFromModel(modelList.get(i)) ?: PlantDataEntity()
                    entityList.add(entity)
                }

                return entityList
            }
            return null
        }

        /**
         * entity 轉換 model
         */
        fun flatModelFromEntity(entity: PlantDataEntity?): PlantDataModel? {
            if(entity != null) {
                val model: PlantDataModel = PlantDataModel()

                model.F_AlsoKnown = entity.F_AlsoKnown
                model.F_Brief = entity.F_Brief
                model.F_CID = entity.F_CID
                model.F_Code = entity.F_Code
                model.F_Family = entity.F_Family
                model.F_Feature = entity.F_Feature
                model.F_FunctionApplication = entity.F_FunctionApplication
                model.F_Genus = entity.F_Genus
                model.F_Geo = entity.F_Geo
                model.F_Keywords = entity.F_Keywords
                model.F_Location = entity.F_Location
                model.F_Name_Ch = entity.F_Name_Ch
                model.F_Name_En = entity.F_Name_En
                model.F_Name_Latin = entity.F_Name_Latin
                model.F_Pic01_ALT = entity.F_Pic01_ALT
                model.F_Pic01_URL = entity.F_Pic01_URL
                model.F_Pic02_ALT = entity.F_Pic02_ALT
                model.F_Pic02_URL = entity.F_Pic02_URL
                model.F_Pic03_ALT = entity.F_Pic03_ALT
                model.F_Pic03_URL = entity.F_Pic03_URL
                model.F_Pic04_ALT = entity.F_Pic04_ALT
                model.F_Pic04_URL = entity.F_Pic04_URL
                model.F_Summary = entity.F_Summary
                model.F_Update = entity.F_Update
                model.F_Vedio_URL = entity.F_Vedio_URL
                model.F_Voice01_ALT = entity.F_Voice01_ALT
                model.F_Voice01_URL = entity.F_Voice01_URL
                model.F_Voice02_ALT = entity.F_Voice02_ALT
                model.F_Voice02_URL = entity.F_Voice02_URL
                model.F_Voice03_ALT = entity.F_Voice03_ALT
                model.F_Voice03_URL = entity.F_Voice03_URL
                model.F_pdf01_ALT = entity.F_pdf01_ALT
                model.F_pdf01_URL = entity.F_pdf01_URL
                model.F_pdf02_ALT = entity.F_pdf02_ALT
                model.F_pdf02_URL = entity.F_pdf02_URL

                return model
            }
            return null
        }

        /**
         * entityList 轉換 modelList
         */
        fun flatModelListFromEntityList(entityList: List<PlantDataEntity>?): List<PlantDataModel>? {
            if(entityList != null && entityList.size != 0) {
                val modelList: MutableList<PlantDataModel> = mutableListOf()
                for (i in 0..entityList.size - 1) {
                    val model: PlantDataModel = flatModelFromEntity(entityList.get(i)) ?: PlantDataModel()
                    modelList.add(model)
                }
                return modelList
            }
            return null
        }
    }

    override fun toString(): String {
        return "PlantDataModel(F_AlsoKnown='$F_AlsoKnown', F_Brief='$F_Brief', F_CID='$F_CID', F_Code='$F_Code', F_Family='$F_Family', F_Feature='$F_Feature', F_Function='$F_FunctionApplication', F_Genus='$F_Genus', F_Geo='$F_Geo', F_Keywords='$F_Keywords', F_Location='$F_Location', F_Name_Ch='$F_Name_Ch', F_Name_En='$F_Name_En', F_Name_Latin='$F_Name_Latin', F_Pic01_ALT='$F_Pic01_ALT', F_Pic01_URL='$F_Pic01_URL', F_Pic02_ALT='$F_Pic02_ALT', F_Pic02_URL='$F_Pic02_URL', F_Pic03_ALT='$F_Pic03_ALT', F_Pic03_URL='$F_Pic03_URL', F_Pic04_ALT='$F_Pic04_ALT', F_Pic04_URL='$F_Pic04_URL', F_Summary='$F_Summary', F_Update='$F_Update', F_Vedio_URL='$F_Vedio_URL', F_Voice01_ALT='$F_Voice01_ALT', F_Voice01_URL='$F_Voice01_URL', F_Voice02_ALT='$F_Voice02_ALT', F_Voice02_URL='$F_Voice02_URL', F_Voice03_ALT='$F_Voice03_ALT', F_Voice03_URL='$F_Voice03_URL', F_pdf01_ALT='$F_pdf01_ALT', F_pdf01_URL='$F_pdf01_URL', F_pdf02_ALT='$F_pdf02_ALT', F_pdf02_URL='$F_pdf02_URL')"
    }

}