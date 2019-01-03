package youmeee.co.jp.clippablelayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ClippableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private val backGroundPaint = Paint()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val porterDuffXferMode: PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

    private var clipList: List<Rect> = mutableListOf()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backGroundPaint)
        paint.xfermode = porterDuffXferMode

        clipList.map { toClipCircle(it) }.forEach {
            canvas.drawCircle(it.centerX, it.centerY, it.radius, paint)
        }
    }

    fun setBackGroundColor(color: Int) {
        backGroundPaint.color = color
    }

    fun setClipRect(rectList: List<Rect>) {
        this.clipList = rectList
        invalidate()
    }

    private fun toClipCircle(clipRect: Rect): ClipCircle {
        val centerX = clipRect.left + (clipRect.right - clipRect.left) / 2
        val centerY = clipRect.top + (clipRect.bottom - clipRect.top) / 2
        val radius = (clipRect.right - clipRect.left) / 2
        return ClipCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat())
    }

    private class ClipCircle(
        val centerX: Float,
        val centerY: Float,
        val radius: Float
    )
}