package com.hikari.jacksonsyu_ddt_test.model

import androidx.annotation.Nullable

data class MuseumDataModel(
    val E_Category: String? = null,
    val E_Geo: String? = null,
    val E_Info: String? = null,
    val E_Memo: String? = null,
    val E_Name: String? = null,
    val E_Pic_URL: String? = null,
    val E_URL: String? = null,
    val E_no: String? = null
){
    override fun toString(): String {
        return "MuseumDataModel(E_Category='$E_Category', E_Geo='$E_Geo', E_Info='$E_Info', E_Memo='$E_Memo', E_Name='$E_Name', E_Pic_URL='$E_Pic_URL', E_URL='$E_URL', E_no='$E_no')"
    }
}