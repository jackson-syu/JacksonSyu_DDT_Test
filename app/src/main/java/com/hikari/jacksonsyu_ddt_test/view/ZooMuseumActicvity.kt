package com.hikari.jacksonsyu_ddt_test.view

import android.Manifest
import android.util.Log
import android.view.View
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.BaseActivity
import com.hikari.jacksonsyu_ddt_test.databinding.ActivityZooMuseumActicvityBinding
import com.hikari.jacksonsyu_ddt_test.permission.PermissionData
import com.hikari.jacksonsyu_ddt_test.permission.PermissionHelper
import com.hikari.jacksonsyu_ddt_test.util.ViewHelper
import com.hikari.jacksonsyu_ddt_test.viewmodel.ZooMuseumActViewModel

/**
 * Created by hikari on 2020/9/23
 */
class ZooMuseumActicvity : BaseActivity<ActivityZooMuseumActicvityBinding>() {

    lateinit var viewModel: ZooMuseumActViewModel
    var fragmentManager: FragmentManager? = null

    //permission
    var permissionHelper: PermissionHelper? = null
    private var myPermissions: MutableList<PermissionData> = mutableListOf()
    private var openPermissionCount: Int = 0
    //多權限requestCode
    private val MULTIPLE_PREMISSIONS = 9099

    companion object {
        private const val TAG = "ZooMuseumActicvity"
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_zoo_museum_acticvity
    }

    override fun initAct() {

        initPermission()

//        initVVM()
    }

    private fun initVVM() {
        viewModel = ZooMuseumActViewModel()

        mBinding.vm = viewModel

        initView()
    }

    private fun initPermission() {

        //20200109 改寫法
        val write_external_storage_data = PermissionData()
        write_external_storage_data.permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        write_external_storage_data.hintMessage = resources.getString(R.string.permissions_notifi_write_external_storage)

        myPermissions.add(write_external_storage_data)

        permissionHelper = PermissionHelper()
        permissionHelper!!.setOnMultiPremissonListener(object : PermissionHelper.OnMultiPremissonListener {
            override fun OnPremissonPostExecute(permission: String?, pos: Int) {
                openPermissionCount++
                if(openPermissionCount == myPermissions.size) {
                    initVVM()
                }
            }
        })
        permissionHelper!!.setOnMultiRefuseListener(object : PermissionHelper.OnMultiRefuseListener {
            override fun OnRefusePostExecute(permission: String?, pos: Int) {
                initVVM()
            }

        })
        permissionHelper!!.multiplePermissionMCheck(this, myPermissions, this, MULTIPLE_PREMISSIONS)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode) {
            MULTIPLE_PREMISSIONS -> initVVM()
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun initView() {

        Log.d(TAG, "initView")

        fragmentManager = supportFragmentManager

        toFragment(MuseumListFragment.newInstance())
//        toFragment(PlantListFragment.newInstance())
//        toFragment(PlantDataFragment.newInstance())
    }

    fun toFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment, fragment.javaClass.name)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun floatUpFragment(fragment: Fragment) {

        mBinding.webFragment.visibility = View.VISIBLE
        mBinding.webFragment.setOnClickListener(null)

        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.web_fragment, fragment, fragment.javaClass.name)
        fragmentTransaction.commitAllowingStateLoss()

        var animation: Animation? = ViewHelper.prepareAnimation(this, R.anim.transform_up, null)
        mBinding.webFragment.startAnimation(animation)
    }

    fun floatDownFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.transform_no_move, R.anim.transform_down)
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        onBack()
    }

    fun onBack() {
        var fragment: Fragment? = fragmentManager?.findFragmentById(R.id.main_fragment)
        if(fragment != null) {
            if(fragment is PlantListFragment) {
                fragment.mBinding.plantListBack.callOnClick()
            }else if(fragment is PlantDataFragment) {
                fragment.mBinding.plantDataBack.callOnClick()
            }
        }
    }
}