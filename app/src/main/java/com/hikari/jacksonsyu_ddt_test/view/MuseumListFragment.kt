package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.adapter.MuseumListAdapter
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentMuseumListBinding
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
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

        initData()

        mBinding.vm = viewModel
        mBinding.presenter = this

        initRecyclerView(mutableListOf())

    }

    private fun initData() {
        viewModel.getMuseumLiveData()?.observe(this, object : Observer<List<MuseumDataModel>> {
            override fun onChanged(museumListData: List<MuseumDataModel>?) {
                Log.d(TAG, "initData onChanged ~")
                initRecyclerView(museumListData)
            }
        })
    }

    private fun initRecyclerView(museumListData: List<MuseumDataModel>?) {

        if(museumListData?.size != 0) {
            mBinding.museumListNoData.visibility = View.GONE
            mBinding.museumListRecyclerview.visibility = View.VISIBLE
            mBinding.museumListRecyclerview.layoutManager = LinearLayoutManager(context)
            var adapter = MuseumListAdapter(context!!, activity!!, viewModel)
            mBinding.museumListRecyclerview.adapter = adapter
            adapter.setMuseumListData(museumListData)
        }else{
            mBinding.museumListRecyclerview.visibility = View.GONE
            mBinding.museumListNoData.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.museum_list_menu -> onMenuClick()
        }
    }

    private fun onMenuClick() {
        viewModel.onMenuClick(context!!)
    }


}