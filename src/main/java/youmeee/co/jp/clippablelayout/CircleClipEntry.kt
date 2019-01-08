package youmeee.co.jp.clippablelayout

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class CircleClipEntry : ClipEntry() {

    override fun clip(canvas: Canvas, paint: Paint) {

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