package youmeee.co.jp.clippablelayout

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.annotation.IdRes

/**
 * ClipEntry
 * The class expresses the object which is clipped.
 *
 * @param resId resources id of the target which is clipped.
 */
abstract class ClipEntry(@IdRes val resId: Int) {

    constructor() : this(0)

    abstract fun clip(canvas: Canvas, paint: Paint, decorRect: Rect)
}