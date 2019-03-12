package jp.co.youmeee.clipper

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

/**
 * ClipEntry
 * The class expresses the object which is clipped.
 *
 * @param resId resources id of the target which is clipped.
 */
abstract class ClipEntry {
    abstract val targetView: View
    abstract fun clip(canvas: Canvas, paint: Paint, decorRect: Rect)
}