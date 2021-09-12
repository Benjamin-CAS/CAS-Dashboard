package com.cas.casdashboard.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.cas.casdashboard.R
import com.cas.casdashboard.util.Constants.px
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
    private val rect = Rect()
    private var progress = 0f
    private var textNum = "0"
    private val pmNum = "PM2.5"
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

    override fun onDraw(canvas: Canvas) {
        paint.apply {
            strokeWidth = 18.px
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            color = ContextCompat.getColor(context!!,R.color.progress_bgc_color)
        }
        val arcRadius = min(width,height) / 2f - 9.px  // 半径
        val arcTop = height / 2f - arcRadius
        val arcStart = width / 2f - arcRadius
        val arcBottom = height / 2f + arcRadius
        val arcEnd =  width / 2f + arcRadius
        canvas.drawArc(arcStart,arcTop,arcEnd,arcBottom,
        90 + OPEN_ANGLE / 2f,360 - OPEN_ANGLE,false,paint)
        paint.color = Color.parseColor("#E6D901")
        canvas.drawArc(arcStart,arcTop,arcEnd,arcBottom,
            90 + OPEN_ANGLE / 2f,progress,false,paint)
        paint.apply {
            style = Paint.Style.FILL
            textSize = 110.px
        }
        paint.getTextBounds(textNum,0,textNum.length,rect)
        canvas.drawText(textNum,width / 2f,height / 2f - (rect.top + rect.bottom) / 1.5f,paint)
        paint.apply {
            textSize = 30.px
            color = Color.WHITE
        }
        canvas.drawText(pmNum,width / 2f,arcTop + 4f * 18.px,paint)
        paint.textSize = 14.px
        canvas.drawText(unitPm,width / 2f,arcTop + 5f * 18.px,paint)
    }
    fun setNumTextWithProgressSweepAngle(text:String){
        val mText = text.toFloat()
        if (mText > 100f) {
            Snackbar.make(this,"Maximum 100",Snackbar.LENGTH_SHORT).show()
        } else {
            startAnimator(mText * 2.4f)
        }
    }
    private fun startAnimator(progressValue:Float){
        mAnimator = ValueAnimator.ofFloat(0f,progressValue)
        mAnimator.apply {
            duration = 800
            addUpdateListener {
                progress = it.animatedValue as Float
                textNum = ((it.animatedValue as Float) / 2.4).toInt().toString()
                postInvalidate()
            }
        }
        mAnimator.start()
    }
    companion object {
        const val OPEN_ANGLE = 120f
        private const val TAG = "MonitorDashboardView"
    }
}