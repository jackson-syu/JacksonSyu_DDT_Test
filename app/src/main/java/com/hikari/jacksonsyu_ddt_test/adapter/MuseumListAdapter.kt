package com.hikari.jacksonsyu_ddt_test.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.AdapterMuseumListItemBinding
import com.hikari.jacksonsyu_ddt_test.model.MuseumDataModel
import com.hikari.jacksonsyu_ddt_test.util.ViewHelper
import com.hikari.jacksonsyu_ddt_test.viewmodel.MuseumListViewModel
import java.lang.Exception

/**
 * Created by hikari on 2020/9/26
 */
class MuseumListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context: Context? = null
    private var activity: Activity? = null
    private var viewModel: MuseumListViewModel? = null
    private var museumListData: List<MuseumDataModel>? = null

    companion object {
        private const val TAG = "MuseumListAdapter"
    }

    constructor(context: Context, activity: Activity, viewModel: MuseumListViewModel) : this() {
        this.context = context
        this.activity = activity
        this.viewModel = viewModel
    }

    fun setMuseumListData(museumListData: List<MuseumDataModel>?) {
        this.museumListData = museumListData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var mBinding: AdapterMuseumListItemBinding = AdapterMuseumListItemBinding.inflate(layoutInflater, parent, false)
        return ItemTypeHolder(mBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemTypeHolder) {
            var museumDataModel: MuseumDataModel? = MuseumDataModel()
            if(museumListData != null && museumListData?.size != 0) {
                 museumDataModel = museumListData?.get(position)
            }
            holder.bind(museumDataModel)
        }
    }

    override fun getItemCount(): Int {
        if(museumListData != null) {
            return museumListData!!.size
        }
        return 0
    }

    /**
     * Item Holder
     */
    inner class ItemTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClickPresenter {
        lateinit var mBinding: AdapterMuseumListItemBinding

        private var museumDataModel: MuseumDataModel? = null

        constructor(mBinding: AdapterMuseumListItemBinding) : this(mBinding.root) {
            this.mBinding = mBinding
        }

        fun bind(museumDataModel: MuseumDataModel?) {
            this.museumDataModel = museumDataModel
            mBinding.vm = viewModel
            mBinding.presenter = this

            initData()

            initImg()
        }

        private fun initData() {
            mBinding.museumListItemTitle.setText(museumDataModel?.E_Name)
            mBinding.museumListItemDetial.setText(museumDataModel?.E_Info)
            var time: String? = museumDataModel?.E_Memo
            if(time == null || time.equals("")) {
                try {
                    time = context?.resources?.getString(R.string.museum_no_time)
                }catch (e: Exception) {
                    time = "無休館資訊"
                }
            }
            mBinding.museumListItemTime.setText(time)
        }

        private fun initImg() {
            Glide.with(context).load(museumDataModel?.E_Pic_URL).into(mBinding.museumListItemImg)
            var imgWidth = ViewHelper.converDpToPiexl(120f, context!!).toInt()
            var imgHeight = imgWidth
            mBinding.museumListItemImg.set(imgWidth, imgHeight, 0f, 0f)
        }

        override fun onClick(v: View) {
            when(v.id) {
                R.id.museum_list_item_layout -> itemClick()
            }
        }

        private fun itemClick() {
            viewModel?.onItemClick(activity!!, museumDataModel!!)
        }

    }
}