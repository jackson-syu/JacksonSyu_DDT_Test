package com.hikari.jacksonsyu_ddt_test.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.AdapterMuseumListItemBinding
import com.hikari.jacksonsyu_ddt_test.util.ViewHelper
import com.hikari.jacksonsyu_ddt_test.viewmodel.MuseumListViewModel

/**
 * Created by hikari on 2020/9/26
 */
class MuseumListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mContext: Context? = null
    var viewModel: MuseumListViewModel? = null

    companion object {
        private const val TAG = "MuseumListAdapter"
    }

    constructor(mContext: Context, viewModel: MuseumListViewModel) : this() {
        this.mContext = mContext
        this.viewModel = viewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var mBinding: AdapterMuseumListItemBinding = AdapterMuseumListItemBinding.inflate(layoutInflater, parent, false)
        return ItemTypeHolder(mBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemTypeHolder) {
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ItemTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ClickPresenter {
        lateinit var mBinding: AdapterMuseumListItemBinding
        constructor(mBinding: AdapterMuseumListItemBinding) : this(mBinding.root) {
            this.mBinding = mBinding
        }

        fun bind() {
            mBinding.vm = viewModel
            mBinding.presenter = this

            initImg()
        }

        fun initImg() {
            var imgWidth = ViewHelper.converDpToPiexl(120f, mContext!!).toInt()
            var imgHeight = imgWidth
            mBinding.museumListItemImg.set(imgWidth, imgHeight, 0f, 0f)
        }

        override fun onClick(v: View) {
            when(v.id) {
                R.id.museum_list_item_layout -> itemClick()
            }
        }

        fun itemClick() {
            viewModel?.onItemClick()
        }

    }
}