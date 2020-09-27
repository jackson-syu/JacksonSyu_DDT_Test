package com.hikari.jacksonsyu_ddt_test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hikari.jacksonsyu_ddt_test.view.InAppBrowserFragment.ContentPageCaller

/**
 * Created by hikari on 2020/9/28
 */
class InAppBrowserViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "InAppBrowserViewModel"
    }

    fun onClose(mCaller: ContentPageCaller) {
        mCaller.onClose()
    }
}