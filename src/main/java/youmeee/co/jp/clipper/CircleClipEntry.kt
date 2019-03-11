package youmeee.co.jp.clipper

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class CircleClipEntry(override val targetView: View) : ClipEntry() {

    private val targetGlobalVisibleRect = Rect()
    private var clipMargin: Int = 0

    constructor(target: View, clipMargin: Float) : this(target) {
        this.clipMargin = clipMargin.toInt()
    }

    override fun clip(canvas: Canvas, paint: Paint, decorRect: Rect) {
        targetView.let {
            it.getGlobalVisibleRect(targetGlobalVisibleRect)
            adjustRectPosition(it, decorRect)
            val clipCircle = toClipCircle()
            canvas.drawCircle(clipCircle.centerX, clipCircle.centerY, clipCircle.radius, paint)
        }
    }

    private fun adjustRectPosition(target: View, decorRect: Rect) {
        targetGlobalVisibleRect.left += target.paddingLeft - clipMargin
        targetGlobalVisibleRect.right += clipMargin - target.paddingRight
        targetGlobalVisibleRect.top -= decorRect.top - clipMargin
        targetGlobalVisibleRect.bottom -= decorRect.top + clipMargin
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