package com.hikari.jacksonsyu_ddt_test.util

import android.content.Context
import android.widget.Toast

/**
 * Created by hikari on 2020/9/28.
 */
class ToastHelper {
    companion object {
        private var toast: Toast? = null

        /**
         * 顯示Toast
         * @param context 上下文
         * @param message 要顯示的內容
         */
        fun showToast(context: Context?, message: String?) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            } else {
                toast!!.setText(message)
            }
            toast!!.show()
        }

        /**
         * 顯示Toast
         * @param context 上下文
         * @param resId 要顯示的資源id
         */
        fun showToast(context: Context, resId: Int) {
            showToast(context, context.resources.getText(resId) as String)
        }
    }
}