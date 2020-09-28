package com.hikari.jacksonsyu_ddt_test.model

import com.hikari.jacksonsyu_ddt_test.room.MuseumDataEntity

data class MuseumDataModel(
    var E_Category: String? = "",
    var E_Geo: String? = "",
    var E_Info: String? = "",
    var E_Memo: String? = "",
    var E_Name: String? = "",
    var E_Pic_URL: String? = "",
    var E_URL: String? = "",
    var E_no: String? = ""
){

    companion object {
        private const val TAG = "MuseumDataModel"

        /**
         * model 轉換 entity
         */
        fun flatEntityFromModel(model: MuseumDataModel?): MuseumDataEntity? {
            if(model != null) {
                val entity: MuseumDataEntity = MuseumDataEntity()
                entity.E_no = model.E_no!!
                entity.E_Category = model.E_Category!!
                entity.E_Name = model.E_Name!!
                entity.E_Pic_URL = model.E_Pic_URL!!
                entity.E_Info = model.E_Info!!
                entity.E_Memo = model.E_Memo!!
                entity.E_Geo = model.E_Geo!!
                entity.E_URL = model.E_URL!!

                return entity
            }
            return null
        }

        /**
         * modelList 轉換 entityList
         */
        fun flatEntityListFromModelList(modelList: List<MuseumDataModel>?): List<MuseumDataEntity>? {
            if(modelList != null && modelList.size != 0) {
                val entityList: MutableList<MuseumDataEntity> = mutableListOf()

                for (i in 0..modelList.size - 1) {
                    val entity: MuseumDataEntity = flatEntityFromModel(modelList.get(i)) ?: MuseumDataEntity()
                    entityList.add(entity)
                }

                return entityList
            }
            return null
        }

        /**
         * entity 轉換 model
         */
        fun flatModelFromEntity(entity: MuseumDataEntity?): MuseumDataModel? {
            if(entity != null) {
                val model: MuseumDataModel = MuseumDataModel()

                model.E_no = entity.E_no
                model.E_Category = entity.E_Category
                model.E_Name = entity.E_Name
                model.E_Pic_URL = entity.E_Pic_URL
                model.E_Info = entity.E_Info
                model.E_Memo = entity.E_Memo
                model.E_Geo = entity.E_Geo
                model.E_URL = entity.E_URL

                return model
            }
            return null
        }

        /**
         * entityList 轉換 modelList
         */
        fun flatModelListFromEntityList(entityList: List<MuseumDataEntity>?): List<MuseumDataModel>? {
            if(entityList != null && entityList.size != 0) {
                val modelList: MutableList<MuseumDataModel> = mutableListOf()
                for (i in 0..entityList.size - 1) {
                    val model: MuseumDataModel = flatModelFromEntity(entityList.get(i)) ?: MuseumDataModel()
                    modelList.add(model)
                }
                return modelList
            }
            return null
        }
    }

    override fun toString(): String {
        return "MuseumDataModel(E_Category='$E_Category', E_Geo='$E_Geo', E_Info='$E_Info', E_Memo='$E_Memo', E_Name='$E_Name', E_Pic_URL='$E_Pic_URL', E_URL='$E_URL', E_no='$E_no')"
    }

}