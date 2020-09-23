package com.hikari.jacksonsyu_ddt_test.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.BaseActivity
import com.hikari.jacksonsyu_ddt_test.databinding.ActivityZooMuseumActicvityBinding

/**
 * Created by hikari on 2020/9/23
 */
class ZooMuseumActicvity : BaseActivity<ActivityZooMuseumActicvityBinding>() {

    companion object {
        private const val TAG = "ZooMuseumActicvity"
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_zoo_museum_acticvity
    }

    override fun initAct() {
        TODO("Not yet implemented")
    }

}