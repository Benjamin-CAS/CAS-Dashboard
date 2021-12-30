package com.cas.casdashboard.customview

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.cas.casdashboard.R
import com.cas.casdashboard.util.Constants.formats
import com.cas.casdashboard.util.Constants.px
import com.cas.casdashboard.util.Constants.textPx
import com.google.android.material.snackbar.Snackbar
import kotlin.math.min

/**
 * @author Benjamin
 * @description:
 * @date :2021 17:38
 */
class MonitorDashboardView(context: Context,attrs:AttributeSet): View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    private var progress = 0f
    private var textNum = "0"
    private var pmNum = "PM2.5"
    private val unitPm = "μg/m3"
    private lateinit var mAnimator:ValueAnimator
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width = if (widthMode == MeasureSpec.EXACTLY) widthSize else min(widthSize,suggestedMinimumWidth)
        val height = if (heightMode == MeasureSpec.EXACTLY) heightSize else min(heightSize,suggestedMinimumHeight)
        setMeasuredDimension(width,height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        paint.apply {
            strokeWidth = 12.px
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            color = ContextCompat.getColor(context!!,R.color.progress_bgc_color)
        }

        val arcRadius = min(width,height) / 2f - 6.px  // 半径
        val rect = RectF(
            width / 2 - arcRadius,
            height / 2 - arcRadius,
            width / 2 + arcRadius,
            height / 2 + arcRadius
        )

        canvas.drawArc(rect, 90 + OPEN_ANGLE / 2f,360 - OPEN_ANGLE,false,paint)
        paint.color = ContextCompat.getColor(context,R.color.progress_bar)
        canvas.drawArc(rect,
            90 + OPEN_ANGLE / 2f,progress,false,paint)
        paint.apply {
            style = Paint.Style.FILL
            textSize = 70.textPx
        }
        val maxTextWidth = 2 * arcRadius - 2 * paint.strokeWidth
        var textNumWidth = paint.measureText(textNum)
        while (textNumWidth > maxTextWidth){
            paint.textSize = paint.textSize - 1.px
            textNumWidth = paint.measureText(textNum)
        }
        canvas.drawText(textNum,width / 2f,height / 2f + paint.strokeWidth,paint)
        paint.apply {
            textSize = 14.textPx
            color = Color.WHITE
        }
        var textPmNumWidth = paint.measureText(pmNum)
        while (textPmNumWidth > maxTextWidth){
            paint.textSize = paint.textSize - 1.textPx
            textPmNumWidth = paint.measureText(pmNum)
        }
        canvas.drawText(pmNum,width / 2f,height / 2f - arcRadius + paint.strokeWidth / 2 * 5,paint)
        paint.textSize = 10.textPx
        var textUnitPm = paint.measureText(unitPm)
        while (textUnitPm > maxTextWidth){
            paint.textSize = paint.textSize - 1.textPx
            textUnitPm = paint.measureText(unitPm)
        }
        canvas.drawText(unitPm,width / 2f,height / 2f - arcRadius + paint.strokeWidth / 2 * 7,paint)
    }
    fun setNumTextWithProgressSweepAngle(text:String){
        val mText = text.toFloat()
        if (mText > 100f) {
            Snackbar.make(this,"Maximum 100",Snackbar.LENGTH_SHORT).show()
        } else {
            startAnimator(mText * arcAngle)
        }
    }
    fun setLabelText(text:String){
        pmNum = text
    }
    private fun startAnimator(progressValue:Float){
        mAnimator = ValueAnimator.ofFloat(0f,progressValue)
        mAnimator.apply {
            duration = 1000
            addUpdateListener {
                progress = it.animatedValue as Float
                textNum = ((it.animatedValue as Float) / arcAngle).formats
                postInvalidate()
            }
        }
        mAnimator.start()
    }
    companion object {
        const val OPEN_ANGLE = 160f
        const val arcAngle = 3.6f - (OPEN_ANGLE / 100)
        private const val TAG = "MonitorDashboardView"
    }
}