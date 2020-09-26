package com.hikari.jacksonsyu_ddt_test.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by hikari on 2020/9/23
 */
open abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var mBinding: T
    lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mView = LayoutInflater.from(this.context).inflate(getLayoutId(), null)
        mBinding = DataBindingUtil.bind(mView)!!
        onBindView(mView, container, savedInstanceState)

        return mView
    }

    abstract fun getLayoutId(): Int
    abstract fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?)
}