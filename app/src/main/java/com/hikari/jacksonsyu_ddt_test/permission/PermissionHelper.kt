package com.hikari.jacksonsyu_ddt_test.permission

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hikari.jacksonsyu_ddt_test.R
import java.util.*

/**
 * Created by hikari on 2017/3/13.
 * 權限工具
 */
class PermissionHelper {
    private val TAG = "PermissionProcess"

    private var onMultiPremissonListener: OnMultiPremissonListener? = null
    private var onMultiRefuseListener: OnMultiRefuseListener? = null
    var allowPermissions: MutableList<String?>? = null
    var refusedPermissions: MutableList<String?>? = null

    //---------------------20180919 多權限使用----------------------
    /**
     *
     * For Android 6.0 permission權限確認 多權限
     *
     * @param mContext
     * @param mActivity
     * @param requestCode
     * @param myPermissions
     * @return
     */
    fun multiplePermissionMCheck(
        mContext: Context?,
        myPermissions: MutableList<PermissionData>?,
        mActivity: Activity,
        requestCode: Int
    ): PermissionHelper {
        //第一次需要發送的權限列表
        allowPermissions = mutableListOf()
        //被拒絕過
        refusedPermissions = mutableListOf()
        if (mContext != null && myPermissions != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (i in myPermissions.indices) {
                val permission = myPermissions[i].permission
                Log.d(TAG, "permission開啟權限~ pos: $i")

                //確認是否不在已授權權限列表中
                if (ContextCompat.checkSelfPermission(
                        mContext,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    //是否已跟系統要過權限(要過但是拒絕了) or 是否需要告訴用戶為什麼要這個權限
                    if (mActivity.shouldShowRequestPermissionRationale(permission)) {
                        Log.d(TAG, "shouldShowRequestPermissionRationale~")

                        // 此处的方法最好做成异步的，而不要阻塞住当前进程，当用户看到解释作出反应后，再次申请权限
                        Log.d(TAG, "mContext: $mContext")

                        //紀錄被拒絕的權限還有幾項
                        refusedPermissions!!.add(permission)

                    } else {
                        //第一次
                        Log.d(
                            TAG,
                            "shouldShowRequestPermissionRationale else~ permission: $permission"
                        )

                        //發送權限請求
                        allowPermissions!!.add(permission)
                    }
                } else {

                    //已授權
                    if (onMultiPremissonListener != null) {
                        onMultiPremissonListener!!.OnPremissonPostExecute(permission, i)
                    }
                }
            }
            if (refusedPermissions!!.size == 0 && allowPermissions!!.size != 0) {
                val newPermissions =
                    arrayOfNulls<String>(allowPermissions!!.size)
                for (j in allowPermissions!!.indices) {
                    newPermissions[j] = allowPermissions!![j]
                }
                //發送權限請求
                ActivityCompat.requestPermissions(mActivity, newPermissions, requestCode)
            }
            if (refusedPermissions!!.size != 0) {
                var message = ""
                for (k in refusedPermissions!!.indices) {

                    //20200114 用原本的文案加上詢問
                    //dialog只出現一次，權限用堆疊
                    if (message == "") {
                        message =
                            mActivity.resources.getString(R.string.permissions_notifi_message) + "，" + mActivity.resources
                                .getString(R.string.permissions_notifi_end)
                    }
                    val nowMessage = message
                    if (k == refusedPermissions!!.size - 1) {
                        AlertDialog.Builder(mContext)
                            .setMessage(message)
                            .setPositiveButton("確定", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, p1: Int) {
                                    Log.d(TAG, "permission拒絕~")
                                    for (m in myPermissions.indices) {
                                        for (refusedPermission in refusedPermissions!!) {
                                            if (myPermissions[m].permission == refusedPermission) {

                                                //拒絕後續 最後一個item才做
                                                if (onMultiRefuseListener != null) {
                                                    onMultiRefuseListener!!.OnRefusePostExecute(
                                                        refusedPermission,
                                                        m
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    dialog?.dismiss()
                                }

                            })
                            .setNegativeButton("開啟權限", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, p1: Int) {
                                    Log.d(
                                        TAG,
                                        "permission開啟權限~ nowMessage: $nowMessage"
                                    )
                                    dialog?.dismiss()
                                    val newPermissions =
                                        arrayOfNulls<String>(refusedPermissions!!.size)
                                    for (p in refusedPermissions!!.indices) {
                                        newPermissions[p] = refusedPermissions!![p]
                                    }
                                    //發送權限請求
                                    ActivityCompat.requestPermissions(
                                        mActivity,
                                        newPermissions,
                                        requestCode
                                    )
                                }

                            })
                            .show()


                        //20200114 測試用
//                        String[] newPermissions = new String[refusedPermissions.size()];
//                        for (int p = 0; p < refusedPermissions.size(); p++) {
//                            newPermissions[p] = refusedPermissions.get(p);
//                        }
//                        ActivityCompat.requestPermissions(mActivity, newPermissions, requestCode);
                    }
                }
            }
        }
        return this
    }

    fun setOnMultiPremissonListener(onMultiPremissonListener: OnMultiPremissonListener?): PermissionHelper {
        this.onMultiPremissonListener = onMultiPremissonListener
        return this
    }

    /**
     * 6.0客製權限監聽 多權限
     */
    interface OnMultiPremissonListener {
        /**
         * 危險權限經許可後的後續事項
         */
        fun OnPremissonPostExecute(permission: String?, pos: Int)
    }

    fun setOnMultiRefuseListener(onMultiRefuseListener: OnMultiRefuseListener?): PermissionHelper {
        this.onMultiRefuseListener = onMultiRefuseListener
        return this
    }

    /**
     * 6.0拒絕權限後續監聽 多權限
     */
    interface OnMultiRefuseListener {
        fun OnRefusePostExecute(permission: String?, pos: Int)
    }

    companion object {
        //20190712
        /**
         * 拒絕的權限數量
         * @param mContext
         * @param myPermissions 權限名稱列表
         * @return
         */
        fun getRefusePermissionCount(
            mContext: Context?,
            myPermissions: ArrayList<PermissionData>?
        ): Int {
            var count = 0
            if (mContext != null && myPermissions != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (i in myPermissions.indices) {
                    val permission = myPermissions[i].permission
                    if (ContextCompat.checkSelfPermission(
                            mContext,
                            permission!!
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        count++
                    }
                }
            }
            return count
        }

        /**
         * 已通過的權限數量
         * @param mContext
         * @param myPermissions 權限名稱列表
         * @return
         */
        fun getOpenPermissionCount(
            mContext: Context?,
            myPermissions: ArrayList<PermissionData>?
        ): Int {
            var count = 0
            if (mContext != null && myPermissions != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (i in myPermissions.indices) {
                    val permission = myPermissions[i].permission
                    if (ContextCompat.checkSelfPermission(
                            mContext,
                            permission!!
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        count++
                    }
                }
            }
            return count
        }
    }
}