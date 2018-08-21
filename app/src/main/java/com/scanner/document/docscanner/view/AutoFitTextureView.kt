package com.scanner.document.docscanner.view

import android.content.Context
import android.util.AttributeSet
import android.view.TextureView
import android.view.View
import timber.log.Timber

/**
 * Created by AnthonyCAS on 8/21/18.
 */


/**
 * Custom view that can be adjusted to a specified aspect ratio
 */
class AutoFitTextureView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0) : TextureView(context, attrs, defStyle) {

    private var ratioWidth = 0
    private var ratioHeight = 0

    /**
     * Sets the aspect ratio for this view.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    fun setAspectRatio(width: Int, height: Int) {
        if (width < 0 || height < 0) {
            throw IllegalArgumentException("Size cannot be negative.")
        }
        ratioWidth = width
        ratioHeight = height
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        if (ratioWidth == 0 || ratioHeight == 0) {
            setMeasuredDimension(width, height)
        } else {
            if (width < height * ratioWidth / ratioHeight) {
                setMeasuredDimension(width, width * ratioHeight / ratioWidth)
                Timber.e(">>> Measuring and setting W: $width, ${width * ratioHeight / ratioWidth}")
            } else {
                setMeasuredDimension(height * ratioWidth / ratioHeight, height)
                Timber.e(">>> Measuring and setting H: ${height * ratioWidth / ratioHeight}, $height")
            }
        }
    }

}