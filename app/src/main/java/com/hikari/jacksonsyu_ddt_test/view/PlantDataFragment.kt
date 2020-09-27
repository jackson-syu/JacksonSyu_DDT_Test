package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentPlantDataBinding
import com.hikari.jacksonsyu_ddt_test.util.ViewHelper
import com.hikari.jacksonsyu_ddt_test.viewmodel.PlantDataViewModel

/**
 * Created by hikari on 2020/9/27
 */
class PlantDataFragment : BaseFragment<FragmentPlantDataBinding>(), ClickPresenter {

    lateinit var viewModel: PlantDataViewModel

    companion object {
        private const val TAG = "PlantDataFragment"

        fun newInstance(): PlantDataFragment {
            val args = Bundle()

            val fragment = PlantDataFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_plant_data
    }

    override fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(PlantDataViewModel::class.java)
        mBinding.vm = viewModel
        mBinding.presenter = this

        initBigImg()
    }

    private fun initBigImg() {

        var windowWidth = ViewHelper.getWindowWidth(activity!!)
        var imgWidth = windowWidth
        var imgHeight = imgWidth * 9 / 16
        mBinding.plantDataImg.set(imgWidth, imgHeight, 0f, 0f)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.plant_data_back -> onBack()
        }
    }

    private fun onBack() {
        viewModel.onBack(activity!!)
    }
}