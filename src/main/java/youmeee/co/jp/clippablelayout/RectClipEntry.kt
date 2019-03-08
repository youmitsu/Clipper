package youmeee.co.jp.clippablelayout

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class RectClipEntry(override val targetView: View) : ClipEntry() {

    private val targetGlobalVisibleRect = Rect()
    private var clipMargin: Int = 0

    constructor(target: View, clipMargin: Float) : this(target) {
        this.clipMargin = clipMargin.toInt()
    }

    override fun clip(canvas: Canvas, paint: Paint, decorRect: Rect) {
        targetView.let {
            it.getGlobalVisibleRect(targetGlobalVisibleRect)
            adjustRectPosition(it, decorRect)
            canvas.drawRect(targetGlobalVisibleRect, paint)
        }
    }

    private fun adjustRectPosition(target: View, decorRect: Rect) {
        targetGlobalVisibleRect.left += target.paddingLeft - clipMargin
        targetGlobalVisibleRect.right += clipMargin - target.paddingRight
        targetGlobalVisibleRect.top -= decorRect.top + clipMargin
        targetGlobalVisibleRect.bottom -= decorRect.top - clipMargin
    }
}