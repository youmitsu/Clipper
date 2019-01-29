package youmeee.co.jp.clippablelayout

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class RectClipEntry() : ClipEntry() {

    private var target: View? = null
    private val targetGlobalVisibleRect = Rect()
    private var clipMargin: Int = 0

    constructor(target: View) : this() {
        this.target = target
    }

    constructor(target: View, clipMargin: Float) : this(target) {
        this.clipMargin = clipMargin.toInt()
    }

    constructor(resId: Int) : this() {
        //TODO
    }

    override fun clip(canvas: Canvas, paint: Paint, decorRect: Rect) {
        target?.let {
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