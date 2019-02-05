package youmeee.co.jp.clippablelayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * ClippableLayout
 */
class ClippableLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    var clippableView: ClippableView? = null
    var descView: View? = null
    val descLayoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

    fun showOverlay(parent: ViewGroup) {
        clippableView?.showOverlay(this)
        descView?.let { addView(it, descLayoutParams) }
        parent.addView(this)
        invalidate()
    }

    fun setDescViewGravity(gravity: Int) {
        descLayoutParams.gravity = gravity
    }

    fun clear() = clippableView?.clear()
}