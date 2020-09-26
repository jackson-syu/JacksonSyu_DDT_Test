package com.hikari.jacksonsyu_ddt_test.coustom

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/**
 * Created by hikari on 2020/9/26
 */
class CropImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatImageView(context, attrs, defStyleAttr) {

    var mExpWidth = 1
    var mExpHeight = 1
    var mWidthPercent = 0f
    var mHeightPercent = 0f

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context) : this(context, null) {

    }

    init {
        setUp()
    }

    private fun setUp() {
        scaleType = ScaleType.MATRIX
    }

    fun set(expWidth: Int, expHeight: Int, widthPercent: Float, heightPercent: Float) {
        mExpWidth = expWidth
        mExpHeight = expHeight
        mWidthPercent = widthPercent
        mHeightPercent = heightPercent
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {

        val matrix: Matrix = imageMatrix

        val scale: Float

        val viewWidth = mExpWidth
        val viewHeight = mExpHeight

        var drawableWidth = 0
        var drawableHeight = 0

        if (drawable != null) {
            drawableWidth = drawable.intrinsicWidth
            drawableHeight = drawable.intrinsicHeight
        }
        scale =
            if (drawableWidth * viewHeight > drawableHeight * viewWidth) viewHeight.toFloat() / drawableHeight.toFloat() else viewWidth.toFloat() / drawableWidth.toFloat()

        val viewToDrawableWidth = viewWidth / scale
        val viewToDrawableHeight = viewHeight / scale
        val xOffset = mWidthPercent * (drawableWidth - viewToDrawableWidth)
        val yOffset = mHeightPercent * (drawableHeight - viewToDrawableHeight)

        val drawableRect =
            RectF(xOffset, yOffset, xOffset + viewToDrawableWidth, yOffset + viewToDrawableHeight)
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL)

        imageMatrix = matrix

        return super.setFrame(l, t, r, b)
    }
}