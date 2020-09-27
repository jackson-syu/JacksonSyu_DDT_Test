package com.hikari.jacksonsyu_ddt_test.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProviders
import com.hikari.jacksonsyu_ddt_test.R
import com.hikari.jacksonsyu_ddt_test.base.BaseFragment
import com.hikari.jacksonsyu_ddt_test.base.ClickPresenter
import com.hikari.jacksonsyu_ddt_test.databinding.FragmentInAppBrowserBinding
import com.hikari.jacksonsyu_ddt_test.viewmodel.InAppBrowserViewModel


/**
 * Created by hikari on 2020/9/28
 */
class InAppBrowserFragment : BaseFragment<FragmentInAppBrowserBinding>(), ClickPresenter {

    lateinit var viewModel: InAppBrowserViewModel

    private var mCaller: ContentPageCaller? = null

    companion object {
        private const val TAG = "InAppBrowserFragment"

        private const val WEB_TITLE = "webviewTitle"
        private const val WEB_URL = "webviewUrl"
        fun newInstance(title: String?, url: String?): InAppBrowserFragment {
            val args = Bundle()
            args.putString(WEB_TITLE, title)
            args.putString(WEB_URL, url)

            val fragment = InAppBrowserFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_in_app_browser
    }

    override fun onBindView(view: View?, container: ViewGroup?, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(InAppBrowserViewModel::class.java)

        mBinding.presenter = this

        var title: String = arguments?.getString(WEB_TITLE) as String
        initView(title)
        var url: String = arguments?.getString(WEB_URL) as String
        initWebView(url)
    }

    private fun initView(title: String) {
        mBinding.inAppBrowserTitle.setText(title)
    }

    private fun initWebView(url: String) {

        var webSettings: WebSettings= mBinding.inAppBrowserWeb.settings
        webSettings.javaScriptEnabled = true

        mBinding.inAppBrowserWeb.webViewClient = WebViewClient()
        mBinding.inAppBrowserWeb.loadUrl(url)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.in_app_browser_close -> onClose()
        }
    }

    private fun onClose() {
        if(mCaller != null) {
            viewModel.onClose(mCaller!!)
        }
    }

    /**
     * 關閉動畫用
     */
    interface ContentPageCaller {
        fun onClose()
        fun onContentPageAnimEnd()
    }

    /**
     * 設定動畫效果回呼
     * @param caller
     */
    fun setCaller(caller: ContentPageCaller) {
        mCaller = caller
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {

        if(nextAnim == 0) {
            return null
        }

        val anim: Animation = AnimationUtils.loadAnimation(activity, nextAnim)

        if (!enter) anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }
            override fun onAnimationRepeat(animation: Animation) {

            }
            override fun onAnimationEnd(animation: Animation) {
                if (mCaller != null) {
                    mCaller?.onContentPageAnimEnd()
                }
            }
        })

        val animationWrapper = AnimationSet(false)
        animationWrapper.addAnimation(anim)

        return animationWrapper

    }
}