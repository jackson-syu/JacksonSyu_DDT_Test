package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentPlantDataBinding
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.model.PlantDataModel
import com.hikari.jacksonsyu_ddt_test.util.ViewHelper
import com.hikari.jacksonsyu_ddt_test.viewmodel.PlantDataViewModel

/**
 * Created by hikari on 2020/9/27
 */
class PlantDataFragment : BaseFragment<FragmentPlantDataBinding>(), ClickPresenter {

    lateinit var viewModel: PlantDataViewModel

    private var museumDataModel: MuseumDataModel? = null
    private var plantDataModel: PlantDataModel? = null

    companion object {
        private const val TAG = "PlantDataFragment"

        fun newInstance(): PlantDataFragment {
            val args = Bundle()

            val fragment = PlantDataFragment()
            fragment.arguments = args
            return fragment
        }
    }

    //TODO 暫時
    fun setMuseumData(museumDataModel: MuseumDataModel?) {
        this.museumDataModel = museumDataModel
    }

    fun setPlantData(plantDataModel: PlantDataModel?) {
        this.plantDataModel = plantDataModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_plant_data
    }

    override fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(PlantDataViewModel::class.java)
        mBinding.vm = viewModel
        mBinding.presenter = this

        initData()

        initBigImg()
    }

    private fun initData() {
        mBinding.plantDataTitle.setText(plantDataModel?.F_Name_Ch)
        mBinding.plantDataNameCh.setText(plantDataModel?.F_Name_Ch)
        mBinding.plantDataNameEn.setText(plantDataModel?.F_Name_En)
        mBinding.plantDataAlsoKnown.setText(plantDataModel?.F_AlsoKnown)
        mBinding.plantDataBrief.setText(plantDataModel?.F_Brief)
        mBinding.plantDataFeature.setText(plantDataModel?.F_Feature)
        Log.d(TAG, "F_F_FunctionApplication: " + plantDataModel?.F_F_FunctionApplication)
        mBinding.plantDataFunction.setText(plantDataModel?.F_F_FunctionApplication)
        mBinding.plantDataUddateTime.setText(plantDataModel?.F_Update)
    }

    private fun initBigImg() {
        if(plantDataModel != null) {
            Glide.with(context).load(plantDataModel?.F_Pic01_URL).into(mBinding.plantDataImg)
        }
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
        viewModel.onBack(activity!!, museumDataModel!!)
    }

}