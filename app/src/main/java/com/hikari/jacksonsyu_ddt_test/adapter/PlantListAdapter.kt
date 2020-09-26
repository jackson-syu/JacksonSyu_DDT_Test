package com.hikari.jacksonsyu_ddt_test.adapter

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.AdapterPlantListHeaderBinding
import com.hikari.jacksonsyu_ddt_test.databinding.AdapterPlantListItemBinding
import com.hikari.jacksonsyu_ddt_test.util.ViewHelper
import com.hikari.jacksonsyu_ddt_test.viewmodel.PlantListViewModel
import kotlinx.android.synthetic.main.adapter_plant_list_item.view.*


/**
 * Created by hikari on 2020/9/26
 */
class PlantListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var viewModel: PlantListViewModel? = null
    private var mActivity: Activity? = null

    companion object {
        private const val TAG = "PlantListAdapter"

        private const val ITEM_TYPE = 0;
        private const val HEADER_TYPE = 1;

    }

    constructor(mActivity: Activity, viewModel: PlantListViewModel) : this() {
        this.viewModel = viewModel
        this.mActivity = mActivity
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
            holder.bind(position)
        }else if(holder is ItemTypeHolder) {
            val newPos = position - 1
            holder.bind(newPos)
        }
    }

    override fun getItemCount(): Int {
        return 20 + 1
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) {
            return HEADER_TYPE
        }
        return ITEM_TYPE
    }

    inner class ItemTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClickPresenter {
        lateinit var mBinding: AdapterPlantListItemBinding
        var pos: Int = -1
        constructor(mBinding: AdapterPlantListItemBinding) : this(mBinding.root) {
            this.mBinding = mBinding
        }

        fun bind(pos: Int) {
            this.pos = pos
            mBinding.vm = viewModel
            mBinding.presenter = this

            initImg()
        }

        fun initImg() {
            var imgWidth = ViewHelper.converDpToPiexl(120f, mActivity!!).toInt()
            var imgHeight = imgWidth
            mBinding.plantListItemImg.set(imgWidth, imgHeight, 0f, 0f)
        }

        override fun onClick(v: View) {
            when(v.id) {
                R.id.plant_list_item_layout -> onItemClick()
            }
        }

        private fun onItemClick() {
            viewModel?.onItemClick()
        }
    }

    inner class HeaderTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClickPresenter {
        lateinit var mBinding: AdapterPlantListHeaderBinding
        var pos: Int = -1
        constructor(mBinding: AdapterPlantListHeaderBinding) : this(mBinding.root) {
            this.mBinding = mBinding
        }

        fun bind(pos: Int) {
            this.pos = pos
            mBinding.vm = viewModel
            mBinding.presenter = this

            initBigImg()
        }

        private fun initBigImg() {

            var windowWidth = ViewHelper.getWindowWidth(mActivity!!)
            var imgWidth = windowWidth
            var imgHeight = imgWidth * 9 / 16
            mBinding.plantListHeaderImg.set(imgWidth, imgHeight, 0f, 0f)
        }

        override fun onClick(v: View) {
            TODO("Not yet implemented")
        }
    }
}