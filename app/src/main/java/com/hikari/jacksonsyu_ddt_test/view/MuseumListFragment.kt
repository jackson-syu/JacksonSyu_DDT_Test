package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.adapter.MuseumListAdapter
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentMuseumListBinding
import com.hikari.jacksonsyu_ddt_test.viewmodel.MuseumListViewModel

/**
 * Created by hikari on 2020/9/23
 */
class MuseumListFragment : BaseFragment<FragmentMuseumListBinding>(), ClickPresenter {

    lateinit var viewModel: MuseumListViewModel

    companion object {
        private const val TAG = "MuseumListFragment"

        fun newInstance(): MuseumListFragment{
            val args = Bundle()

            val fragment = MuseumListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_museum_list
    }

    override fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this).get(MuseumListViewModel::class.java)
        mBinding.vm = viewModel
        mBinding.presenter = this

        initRecyclerView()

    }

    private fun initRecyclerView() {

        mBinding.museumListRecyclerview.layoutManager = LinearLayoutManager(context)
        mBinding.museumListRecyclerview.adapter = MuseumListAdapter(context!!, viewModel)

    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.museum_list_menu -> onMenuClick()
        }
    }

    private fun onMenuClick() {
        viewModel.onMenuClick()
    }


}