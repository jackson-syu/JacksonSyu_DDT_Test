package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentMuseumListBinding

/**
 * Created by hikari on 2020/9/23
 */
class MuseumListFragment : BaseFragment<FragmentMuseumListBinding>(), ClickPresenter {

    companion object {
        private const val TAG = "MuseumListFragment"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_museum_list
    }

    override fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View) {
        TODO("Not yet implemented")
    }


}