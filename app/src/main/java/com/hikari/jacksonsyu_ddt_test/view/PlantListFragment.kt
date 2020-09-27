package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.adapter.PlantListAdapter
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentPlantListBinding
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.model.PlantDataModel
import com.hikari.jacksonsyu_ddt_test.viewmodel.PlantListViewModel
import java.util.stream.Stream

/**
 * Created by hikari on 2020/9/26
 */
class PlantListFragment : BaseFragment<FragmentPlantListBinding>(), ClickPresenter{

    private lateinit var viewModel: PlantListViewModel

    private var museumDataModel: MuseumDataModel? = null

    private var adapter: PlantListAdapter? = null

    companion object {
        private const val TAG = "PlantListFragment"

        fun newInstance(): PlantListFragment{
            val args = Bundle()

            val fragment = PlantListFragment()

            fragment.arguments = args
            return fragment
        }
    }

    fun setMuseumData(museumDataModel: MuseumDataModel?) {
        this.museumDataModel = museumDataModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_plant_list
    }

    override fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(PlantListViewModel::class.java)

        initData()

        mBinding.vm = viewModel
        mBinding.presenter = this

        initView()

        initRecyclerView(mutableListOf());
    }

    private fun initData() {
        viewModel.getPlantLiveData()?.observe(this, object : Observer<List<PlantDataModel>> {
            override fun onChanged(plantListData: List<PlantDataModel>?) {
                Log.d(TAG, "initData onChanged ~")
                changePlantData(plantListData)
            }

        })
    }

    private fun initView() {
        if(museumDataModel != null) {
            mBinding.plantListTitle.setText(museumDataModel?.E_Name)
        }
    }

    private fun initRecyclerView(plantListData: List<PlantDataModel>?) {

        mBinding.plantListRecyclerview.visibility = View.VISIBLE
        mBinding.plantListRecyclerview.layoutManager = LinearLayoutManager(context)
        adapter = PlantListAdapter(context!!, activity!!, viewModel)
        mBinding.plantListRecyclerview.adapter = adapter
        adapter?.setMuseumData(museumDataModel)

    }

    private fun changePlantData(plantListData: List<PlantDataModel>?) {
        if(adapter != null && plantListData?.size != 0) {

            var nowPlantListData: MutableList<PlantDataModel> = mutableListOf()

            var nowLocal: String? = museumDataModel?.E_Name

            for (i in 0..plantListData!!.size - 1) {
                var localGroup: String? = plantListData.get(i).F_Location
                if(localGroup!!.contains(nowLocal!!) || localGroup.contains("全園普遍分布")) {
                    nowPlantListData.add(plantListData.get(i))
                }
            }

            adapter?.setPlantListData(nowPlantListData)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.plant_list_back -> onBack()
        }
    }

    private fun onBack() {
        viewModel.onBack(activity!!)
    }

}