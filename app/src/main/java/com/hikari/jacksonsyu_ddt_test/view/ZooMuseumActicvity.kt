package com.hikari.jacksonsyu_ddt_test.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.BaseActivity
import com.hikari.jacksonsyu_ddt_test.databinding.ActivityZooMuseumActicvityBinding
import com.hikari.jacksonsyu_ddt_test.viewmodel.ZooMuseumActViewModel

/**
 * Created by hikari on 2020/9/23
 */
class ZooMuseumActicvity : BaseActivity<ActivityZooMuseumActicvityBinding>() {

    lateinit var viewModel: ZooMuseumActViewModel
    var fragmentManager: FragmentManager? = null

    companion object {
        private const val TAG = "ZooMuseumActicvity"
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_zoo_museum_acticvity
    }

    override fun initAct() {
        viewModel = ZooMuseumActViewModel()

        mBinding.vm = viewModel

        initView()
    }

    fun initView() {

        Log.d(TAG, "initView")

        fragmentManager = supportFragmentManager

//        toFragment(MuseumListFragment.newInstance())
//        toFragment(PlantListFragment.newInstance())
        toFragment(PlantDataFragment.newInstance())
    }

    fun toFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment, fragment.javaClass.name)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        onBack()
    }

    fun onBack() {
        var fragment: Fragment? = fragmentManager?.findFragmentById(R.id.main_fragment)
        if(fragment != null) {
            if(fragment is PlantListFragment) {
                toFragment(MuseumListFragment.newInstance())
            }else if(fragment is PlantDataFragment) {
                toFragment(PlantListFragment.newInstance())
            }
        }
    }
}