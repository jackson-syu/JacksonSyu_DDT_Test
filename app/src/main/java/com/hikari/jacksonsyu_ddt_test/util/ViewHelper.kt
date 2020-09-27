package com.hikari.jacksonsyu_ddt_test.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.animation.Animation
import android.view.animation.AnimationUtils


/**
 * Created by hikari on 2020/9/26
 */
class ViewHelper {
    companion object {
        private const val TAG = "ViewHelper"

        fun getWindowWidth(mActivity: Activity): Int {
            val metrics = DisplayMetrics()
            mActivity?.windowManager?.defaultDisplay?.getMetrics(metrics)
            return metrics.widthPixels
        }

        fun converDpToPiexl(dp: Float, context: Context): Float {
            return dp * getDensity(context)
        }

        fun converPiexlToDp(px: Float, context: Context): Float {
            return px / getDensity(context)
        }

        fun getDensity(context: Context): Float {
            val metrics = context.resources.displayMetrics
            return metrics.density
        }

        fun prepareAnimation(context: Context?, animResId: Int, runOnEnd: Runnable?): Animation? {
            val animation: Animation = AnimationUtils.loadAnimation(context, animResId)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    runOnEnd?.run()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            return animation
        }


    }
}