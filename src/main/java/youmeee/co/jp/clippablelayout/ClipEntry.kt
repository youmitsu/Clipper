package youmeee.co.jp.clippablelayout

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.annotation.IdRes

abstract class ClipEntry(@IdRes val resId: Int) {

    constructor() : this(0)

    abstract fun clip(canvas: Canvas, paint: Paint, decorRect: Rect)
}