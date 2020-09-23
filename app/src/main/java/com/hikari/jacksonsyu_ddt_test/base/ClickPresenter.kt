package com.hikari.jacksonsyu_ddt_test.base

import android.view.View

/**
 * Created by hikari on 2020/9/23
 */
interface ClickPresenter : View.OnClickListener {
    override fun onClick(v: View)
}