package youmeee.co.jp.clippablelayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class CircleClipEntry(val context: Context) : ClipEntry(context) {

    var target: View? = null
    val targetGlobalVisibleRect = Rect()

    constructor(context: Context, target: View) : this(context) {
        this.target = target
    }

    constructor(context: Context, resId: Int) : this(context) {
        //no-op
    }

    override fun clip(canvas: Canvas, paint: Paint, decorRect: Rect) {
        target?.let {
            it.getGlobalVisibleRect(targetGlobalVisibleRect)
            adjustRectPosition(it, decorRect)
            val clipCircle = toClipCircle()
            canvas.drawCircle(clipCircle.centerX, clipCircle.centerY, clipCircle.radius, paint)
        }
    }

    private fun adjustRectPosition(target: View, decorRect: Rect) {
        targetGlobalVisibleRect.left += target.paddingLeft
        targetGlobalVisibleRect.right -= target.paddingRight
        targetGlobalVisibleRect.top -= decorRect.top
        targetGlobalVisibleRect.bottom -= decorRect.top
    }

    private fun toClipCircle(): ClipCircle {
        val centerX = targetGlobalVisibleRect.left + (targetGlobalVisibleRect.right - targetGlobalVisibleRect.left) / 2
        val centerY = targetGlobalVisibleRect.top + (targetGlobalVisibleRect.bottom - targetGlobalVisibleRect.top) / 2
        val radius = (targetGlobalVisibleRect.right - targetGlobalVisibleRect.left) / 2
        return ClipCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat())
    }

    private class ClipCircle(
        val centerX: Float,
        val centerY: Float,
        val radius: Float
    )
}