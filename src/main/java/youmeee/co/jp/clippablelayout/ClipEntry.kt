package youmeee.co.jp.clippablelayout

import android.graphics.Canvas
import android.graphics.Paint

abstract class ClipEntry {
    abstract fun clip(canvas: Canvas, paint: Paint)
}