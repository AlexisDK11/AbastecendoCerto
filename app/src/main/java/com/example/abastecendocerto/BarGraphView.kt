package com.example.abastecendocerto

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BarGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val barPaint1 = Paint()
    private val barPaint2 = Paint()
    private val textPaint = Paint()

    private val barWidth = 120f
    private val spacing = 150f
    private val maxValue = 1f

    private var value1 = 0f
    private var value2 = 0f

    private var label1: String = ""
    private var label2: String = ""

    init {
        barPaint1.color = Color.parseColor("#0AA7C8")
        barPaint2.color = Color.GRAY
        textPaint.color = Color.BLACK
        textPaint.textSize = 32f
        textPaint.textAlign = Paint.Align.CENTER
    }

    fun setValues(value1: Float, value2: Float, label1: String, label2: String) {
        this.value1 = value1
        this.value2 = value2
        this.label1 = label1
        this.label2 = label2
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val height = height.toFloat()
        val centerX = width / 2f
        val startY = height - 200f

        val scale1 = startY / maxValue
        val scale2 = startY / maxValue

        canvas.drawRect(
            centerX - spacing - barWidth / 2,
            startY - value1 * scale1,
            centerX - spacing + barWidth / 2,
            startY,
            barPaint1
        )

        canvas.drawRect(
            centerX + spacing - barWidth / 2,
            startY - value2 * scale2,
            centerX + spacing + barWidth / 2,
            startY,
            barPaint2
        )

        canvas.drawText(label1, centerX - spacing, height, textPaint)
        canvas.drawText(label2, centerX + spacing, height, textPaint)
    }
}

