package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.adapter.PlantListAdapter
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentPlantListBinding
import com.hikari.jacksonsyu_ddt_test.viewmodel.PlantListViewModel

/**
 * Created by hikari on 2020/9/26
 */
class PlantListFragment : BaseFragment<FragmentPlantListBinding>(), ClickPresenter{

    private lateinit var viewModel: PlantListViewModel

    companion object {
        private const val TAG = "PlantListFragment"

        fun newInstance(): PlantListFragment{
            val args = Bundle()

            val fragment = PlantListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_plant_list
    }

    override fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(PlantListViewModel::class.java)
        mBinding.vm = viewModel
        mBinding.presenter = this

        initRecyclerView();
    }

    private fun initRecyclerView() {
        mBinding.plantListRecyclerview.layoutManager = LinearLayoutManager(context)
        mBinding.plantListRecyclerview.adapter = PlantListAdapter(activity!!, viewModel)

    }

    override fun onClick(v: View) {
        TODO("Not yet implemented")
    }

}