package youmeee.co.jp.clippablelayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.IdRes

abstract class ClipEntry(context: Context, @IdRes val resId: Int) {

    constructor(context: Context) : this(context, 0)

    abstract fun clip(canvas: Canvas, paint: Paint, decorRect: Rect)
}