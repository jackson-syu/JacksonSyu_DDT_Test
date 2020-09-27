package com.hikari.jacksonsyu_ddt_test.adapter

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.AdapterPlantListHeaderBinding
import com.hikari.jacksonsyu_ddt_test.databinding.AdapterPlantListItemBinding
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.model.PlantDataModel
import com.hikari.jacksonsyu_ddt_test.util.ViewHelper
import com.hikari.jacksonsyu_ddt_test.viewmodel.PlantListViewModel
import kotlinx.android.synthetic.main.adapter_plant_list_item.view.*


/**
 * Created by hikari on 2020/9/26
 */
class PlantListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context: Context? = null
    private var activity: Activity? = null
    private var viewModel: PlantListViewModel? = null

    private var museumDataModel: MuseumDataModel? = null
    private var plantListData: List<PlantDataModel>? = null

    companion object {
        private const val TAG = "PlantListAdapter"

        private const val ITEM_TYPE = 0;
        private const val HEADER_TYPE = 1;

    }

    constructor(context: Context, activity: Activity, viewModel: PlantListViewModel) : this() {
        this.context = context
        this.activity = activity
        this.viewModel = viewModel
    }

    fun setMuseumData(museumDataModel: MuseumDataModel?) {
        this.museumDataModel = museumDataModel
    }

    fun setPlantListData(plantListData: List<PlantDataModel>?) {
        this.plantListData = plantListData
        notifyItemRangeChanged(1, itemCount - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        if(viewType == HEADER_TYPE) {
            var mBinding: AdapterPlantListHeaderBinding = AdapterPlantListHeaderBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return HeaderTypeHolder(mBinding)
        }else{
            var mBinding: AdapterPlantListItemBinding = AdapterPlantListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return ItemTypeHolder(mBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is HeaderTypeHolder) {
            holder.bind()
        }else if(holder is ItemTypeHolder) {
            val newPos = position - 1
            var plantDataModel: PlantDataModel? = PlantDataModel()
            if(plantListData != null && plantListData?.size != 0) {
                plantDataModel = plantListData?.get(newPos)
            }
            holder.bind(plantDataModel)
        }
    }

    override fun getItemCount(): Int {
        if(plantListData != null && plantListData?.size != 0) {
            return plantListData?.size!! + 1
        }
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) {
            return HEADER_TYPE
        }
        return ITEM_TYPE
    }

    /**
     * Item Holder
     */
    inner class ItemTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClickPresenter {
        lateinit var mBinding: AdapterPlantListItemBinding
        private var plantDataModel: PlantDataModel? = null

        constructor(mBinding: AdapterPlantListItemBinding) : this(mBinding.root) {
            this.mBinding = mBinding
        }

        fun bind(plantDataModel: PlantDataModel?) {
            this.plantDataModel = plantDataModel
            mBinding.vm = viewModel
            mBinding.presenter = this

            initData()

            initImg()
        }

        private fun initData() {
            mBinding.plantListItemTitle.setText(plantDataModel?.F_Name_Ch)
            mBinding.plantListItemDetial.setText(plantDataModel?.F_Brief)
        }

        fun initImg() {
            if(plantDataModel != null) {
                Glide.with(context).load(plantDataModel?.F_Pic01_URL).into(mBinding.plantListItemImg)
            }
            var imgWidth = ViewHelper.converDpToPiexl(120f, activity!!).toInt()
            var imgHeight = imgWidth
            mBinding.plantListItemImg.set(imgWidth, imgHeight, 0f, 0f)
        }

        override fun onClick(v: View) {
            when(v.id) {
                R.id.plant_list_item_layout -> onItemClick()
            }
        }

        private fun onItemClick() {
            viewModel?.onItemClick(activity!!, plantDataModel!!, museumDataModel!!)
        }
    }

    /**
     * Header Holder
     */
    inner class HeaderTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClickPresenter {
        lateinit var mBinding: AdapterPlantListHeaderBinding
        constructor(mBinding: AdapterPlantListHeaderBinding) : this(mBinding.root) {
            this.mBinding = mBinding
        }

        fun bind() {
            mBinding.vm = viewModel
            mBinding.presenter = this

            initData()

            initBigImg()
        }

        private fun initData() {
            if(museumDataModel != null) {
                mBinding.plantListHeaderDetail.setText(museumDataModel?.E_Info)
                mBinding.plantListHeaderTime.setText(museumDataModel?.E_Memo)
                mBinding.plantListHeaderArea.setText(museumDataModel?.E_Category)
            }
        }

        private fun initBigImg() {

            if(museumDataModel != null) {
                Glide.with(context).load(museumDataModel?.E_Pic_URL).into(mBinding.plantListHeaderImg)
            }

            var windowWidth = ViewHelper.getWindowWidth(activity!!)
            var imgWidth = windowWidth
            var imgHeight = imgWidth * 9 / 16
            mBinding.plantListHeaderImg.set(imgWidth, imgHeight, 0f, 0f)
        }

        override fun onClick(v: View) {
            when(v.id) {
                R.id.plant_list_header_broswer_btn -> onWebBtn()
            }
        }

        private fun onWebBtn() {
            viewModel?.onBrowserClick(activity!!, museumDataModel?.E_Name, museumDataModel?.E_URL)
        }
    }
}